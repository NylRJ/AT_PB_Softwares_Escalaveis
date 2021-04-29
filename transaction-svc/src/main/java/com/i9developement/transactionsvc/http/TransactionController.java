package br.com.coffeeandit.transaction.http;

import br.com.coffeeandit.transaction.infrastructure.TransactionBusiness;
import br.com.coffeeandit.transaction.config.NotFoundResponse;
import br.com.coffeeandit.transaction.domain.AlteracaoSituacaoDTO;
import br.com.coffeeandit.transaction.domain.TransactionDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1")
public class TransactionController {

    public static final String TRANSACTION_EVENT = "transaction-event";
    @Value("${app.timeout}")
    private int timeout;
    @Value("${app.cacheTime}")
    public int cacheTime;
    private TransactionBusiness transactionBusiness;
    @Value("${app.intervalTransaction}")
    private int intervalTransaction;

    public TransactionController(final TransactionBusiness transactionBusiness) {
        this.transactionBusiness = transactionBusiness;
    }

    @ApiOperation(value = "API responsável por retornar um SSE com Flux com as transações por período agência e conta.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retorno de Fluxo de stream para a query no DynamoDB"
            , response = TransactionDTO.class
    ), @ApiResponse(code = 404, message = "Dados de conta corrente não encontrado")
            , @ApiResponse(code = 400, message = "Parâmetros de requisição inválidos.")})
    @GetMapping(value = "/transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<List<TransactionDTO>>> queryTransaction(
            @ApiParam(name = "conta", required = true, example = "0207")
            @RequestParam("conta") final Long conta,
            @ApiParam(name = "agencia", required = true, example = "07850")
            @RequestParam("agencia") final Long agencia
    ) {

        return Flux.interval(Duration.ofSeconds(intervalTransaction))
                .map(sequence -> ServerSentEvent.<List<TransactionDTO>>builder()
                        .id(String.valueOf(sequence))
                        .event(TRANSACTION_EVENT)
                        .data(transactionBusiness.queryTransactionFewSeconds(agencia, conta))
                        .build())
                .doOnError(throwable -> {
                    log.error(throwable.getMessage(), throwable);
                });


    }

    @ApiOperation(value = "API para alterar a situação de transação financeira")
    @PatchMapping(path = "/transactions/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Retorno para alteração de uma transação", response = TransactionDTO.class),
            @ApiResponse(code = 401, message = "Erro de autenticação dessa API"),
            @ApiResponse(code = 403, message = "Erro de autorização dessa API"),
            @ApiResponse(code = 404, message = "Recurso não encontrado")})
    public ResponseEntity patch(@PathVariable("id") String uuid, @Valid @RequestBody AlteracaoSituacaoDTO alteracaoSituacaoDTO) {
        var item = transactionBusiness.retrieveItem(uuid);
        if (item.isPresent()) {
            var transactionDTO = item.get();
            log.info("Transação recuperada para atualização %s ", transactionDTO);
            transactionDTO.setSituacao(alteracaoSituacaoDTO.getSituacao());
            log.info("Situação da Transação alterada %s ", transactionDTO);
            var updateItem = transactionBusiness.updateAndRetrieveItem(transactionDTO);
            if (updateItem.isPresent()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            }
        }
        throw new NotFoundResponse(String.format("Não foi possível alterar a transação %s", uuid));

    }

    @DeleteMapping(value = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") String uuid, @RequestHeader(name = "content-type", defaultValue = MediaType.APPLICATION_JSON_VALUE) String contentType) {
        var item = transactionBusiness.retrieveItem(uuid);
        if (item.isPresent()) {
            transactionBusiness.removeItem(item.get());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/transactions/block", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TransactionDTO> queryTransactionBlock(
            @RequestParam("conta") final Long conta, @RequestParam("agencia") final Long agencia
    ) {
        return Flux.fromIterable(transactionBusiness.queryTransaction(agencia, conta))
                .limitRate(100).cache(Duration.ofMinutes(5));


    }

    @GetMapping(value = "/transactions/version", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getFakeVersion() {
        return ResponseEntity.ok("V2");


    }

    @GetMapping(value = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> findById(@PathVariable("id") String uuid, @RequestHeader(name = "content-type", defaultValue = MediaType.APPLICATION_JSON_VALUE) String contentType) {
        var item = transactionBusiness.retrieveItem(uuid);
        if (item.isPresent()) {
            return Mono.just(item.get());
        }
        throw new NotFoundResponse("Transação não encontrada");
    }
}