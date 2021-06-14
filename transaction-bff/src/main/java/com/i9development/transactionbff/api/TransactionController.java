package com.i9development.transactionbff.api;

import com.i9development.transactionbff.events.AlteracaoSituacaoDTO;
import com.i9development.transactionbff.events.dto.RequisicaoTransacaoDTO;
import com.i9development.transactionbff.events.dto.TransactionDTO;
import com.i9development.transactionbff.events.dto.TransactionDTO2;
import com.i9development.transactionbff.events.kafka.KafkaSender;
import com.i9development.transactionbff.exception.InfrastructureException;
import com.i9development.transactionbff.http.TransactionHttpService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;

@RestController
@Slf4j
@RequestMapping("/v1")
@Api(tags = "/v1/transactions", value = "Grupo de API's para manipulação de transações financeiras")
public class TransactionController {

    @Value("${app.timeout}")
    private int timeout;
    @Value("${app.retries}")
    public int numberRetries;
    private KafkaSender kafkaSender;
    private TransactionHttpService transactionHttpService;

    public TransactionController(final KafkaSender kafkaSender, final TransactionHttpService transactionHttpService) {
        this.kafkaSender = kafkaSender;
        this.transactionHttpService = transactionHttpService;
    }

    @ApiOperation(value = "API para criar uma transação financeira", response = TransactionDTO.class, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, authorizations = {@Authorization(value = "i9developement", scopes = {@AuthorizationScope(scope = "SCOPE_i9developementRole", description = "Role para consumo")})}
    )
    @PostMapping(path = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Retorno para a inclusão da mensagem no tópico de transações", response = TransactionDTO.class),
            @ApiResponse(code = 401, message = "Erro de autenticação dessa API"),
            @ApiResponse(code = 403, message = "Erro de autorização dessa API"),
            @ApiResponse(code = 404, message = "Recurso não encontrado")}
    )
    public Mono<TransactionDTO> save(@RequestHeader(name = "Authorization") String bearerToken, @Valid @RequestBody RequisicaoTransacaoDTO transactionDTO) {

        return Mono.just(
                kafkaSender.send(transactionDTO)

        ).timeout(Duration.ofSeconds(timeout))
                .doOnSuccess(result -> {
                    log.info(String.format("Property sended.: %s", result.toString()));
                })
                .doOnError(throwable -> {
                    log.error(throwable.getLocalizedMessage());
                    throw new InfrastructureException(throwable);

                })
                .doFinally(signalType -> {
                    log.info(String.format("Finally.: %s", signalType.toString()));
                })
                .doFirst(() -> changeStatusUnanalyzed(transactionDTO))
                .retry(numberRetries);
    }

    @ApiOperation(value = "API para alterar a situação de transação financeira", authorizations = {@Authorization(value = "i9developement", scopes = {@AuthorizationScope(scope = "SCOPE_i9developementRole", description = "Role para consumo")})})
    @PatchMapping(path = "/transactions/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Retorno para alteração de uma transação", response = TransactionDTO.class),
            @ApiResponse(code = 401, message = "Erro de autenticação dessa API"),
            @ApiResponse(code = 403, message = "Erro de autorização dessa API"),
            @ApiResponse(code = 404, message = "Recurso não encontrado")})
    public ResponseEntity patch(@RequestHeader(name = "Authorization") String bearerToken, @PathVariable("id") String uuid, @Valid @RequestBody AlteracaoSituacaoDTO alteracaoSituacaoDTO) {
        transactionHttpService.alterarSituacao(uuid, alteracaoSituacaoDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @ApiOperation(value = "API para remover as transações persistidas", authorizations = {@Authorization(value = "i9developement", scopes = {@AuthorizationScope(scope = "SCOPE_i9developementRole", description = "Role para consumo")})})
    @DeleteMapping(value = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@RequestHeader(name = "Authorization") String bearerToken, @PathVariable("id") String uuid, @RequestHeader(name = "content-type", defaultValue = MediaType.APPLICATION_JSON_VALUE) String contentType) {

        transactionHttpService.removeById(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private void changeStatusUnanalyzed(TransactionDTO transactionDTO) {
        transactionDTO.naoAnalisada();
    }

    @ApiOperation(value = "API para buscar os transações persistidas por agência e conta", authorizations = {@Authorization(value = "i9developement", scopes = {@AuthorizationScope(scope = "SCOPE_i9developementRole", description = "Role para consumo")})})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retorno OK da Lista de transações"),
            @ApiResponse(code = 401, message = "Erro de autenticação dessa API"),
            @ApiResponse(code = 403, message = "Erro de autorização dessa API"),
            @ApiResponse(code = 404, message = "Recurso não encontrado")})
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "agencia", required = true)})
    @GetMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TransactionDTO> queryTransaction(@RequestHeader(name = "Authorization") String bearerToken,
                                                 @RequestParam("conta") final Long conta, @RequestParam("agencia") final Long agencia
    ) {
        return transactionHttpService.queryTransactionBlock(conta, agencia);


    }

    @ApiOperation(value = "API para buscar  transações pelo Id", authorizations = {@Authorization(value = "i9developement", scopes = {@AuthorizationScope(scope = "SCOPE_i9developementRole", description = "Role para consumo")})})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retorno OK com a transação encontrada."),
            @ApiResponse(code = 401, message = "Erro de autenticação dessa API"),
            @ApiResponse(code = 403, message = "Erro de autorização dessa API"),
            @ApiResponse(code = 404, message = "Recurso não encontrado")})
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", required = true)})
    @GetMapping(value = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO2> findById(@RequestHeader(name = "Authorization") String bearerToken, @PathVariable("id") String uuid) {
        return Mono.just(transactionHttpService.findById(uuid));
    }

}

