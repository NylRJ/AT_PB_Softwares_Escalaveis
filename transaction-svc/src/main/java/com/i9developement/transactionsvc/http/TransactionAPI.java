package com.i9developement.transactionsvc.http;

import com.i9developement.transactionsvc.business.TransactionDomain;
import com.i9developement.transactionsvc.domain.TransactionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;



@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v2")
@Api(tags = "/v2/transactions", value = "Grupo de API's para manipulação de transações financeiras")
public class TransactionAPI {


    public TransactionAPI(){}

    private TransactionDomain transactionDomain;

    @ApiOperation(value = "API responsável por criar uma transação.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retorno da transação Criada"
            , response = TransactionDTO.class
    ), @ApiResponse(code = 404, message = "Dados de transação não encontrado")
            , @ApiResponse(code = 400, message = "Parâmetros de requisição inválidos.")})
    @PostMapping
    public Mono<TransactionDTO> criarTransacao(@Valid TransactionDTO transactionDTO) {
        return Mono.just(transactionDomain.inserirTransacao(transactionDTO));
    }

    @ApiOperation(value = "API responsável por aprovar uma transação.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retorno da transação Criada"
            , response = TransactionDTO.class
    ), @ApiResponse(code = 404, message = "Dados de transação não encontrado")
            , @ApiResponse(code = 400, message = "Parâmetros de requisição inválidos.")})
    @PatchMapping
    public Mono<TransactionDTO> aprovarTransacao(@Valid TransactionDTO transactionDTO) {
        return Mono.just(transactionDomain.aprovarTransacao(transactionDTO));
    }

    @ApiOperation(value = "API responsável por rejeitar uma transação.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retorno da transação Criada"
            , response = TransactionDTO.class
    ), @ApiResponse(code = 404, message = "Dados de transação não encontrado")
            , @ApiResponse(code = 400, message = "Parâmetros de requisição inválidos.")})
    @DeleteMapping
    public Mono<TransactionDTO> rejeitarTransacao(@Valid TransactionDTO transactionDTO) {
        return Mono.just(transactionDomain.rejeitarTransacao(transactionDTO));
    }
}
