package com.i9developement.transactionbff.api;

import com.i9developement.transactionbff.events.dto.TransactionDTO;
import com.i9developement.transactionbff.http.TransactionHttpService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/v1")
@Api(tags = "/v1/transactions", value = "Grupo de API's para manipulação de transações financeiras")
public class TransactionController {
    @Value("${app.timeout}")
    private int timeout;
    @Value("${app.retries}")
    public int numberRetries;

    @Autowired
    private TransactionHttpService transactionHttpService;

    public TransactionController(final TransactionHttpService transactionHttpService) {
        this.transactionHttpService = transactionHttpService;
    }

    @ApiOperation(value = "API para buscar  transações pelo Id", authorizations = {@Authorization(value = "i9developement", scopes = {@AuthorizationScope(scope = "SCOPE_i9developementRole", description = "Role para consumo")})})
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(String uuid){
        var data = transactionHttpService.hello(uuid);
        return data;
    }

    @ApiOperation(value = "API para buscar  transações pelo Id", authorizations = {@Authorization(value = "i9developement", scopes = {@AuthorizationScope(scope = "SCOPE_i9developementRole", description = "Role para consumo")})})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retorno OK com a transação encontrada."),
            @ApiResponse(code = 401, message = "Erro de autenticação dessa API"),
            @ApiResponse(code = 403, message = "Erro de autorização dessa API"),
            @ApiResponse(code = 404, message = "Recurso não encontrado")})
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", required = true)})
    @GetMapping(value = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDTO> findById(@RequestHeader(name = "Authorization") String bearerToken, @PathVariable("id") String uuid) {
        return Mono.just(transactionHttpService.findById(uuid));
    }

    @ApiOperation(value = "API para remover as transações persistidas", authorizations = {@Authorization(value = "i9developement", scopes = {@AuthorizationScope(scope = "SCOPE_i9developementRole", description = "Role para consumo")})})
    @DeleteMapping(value = "/transactions/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@RequestHeader(name = "Authorization") String bearerToken, @PathVariable("id") String uuid, @RequestHeader(name = "content-type", defaultValue = MediaType.APPLICATION_JSON_VALUE) String contentType) {

        transactionHttpService.removeById(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
