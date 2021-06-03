package com.i9developement.transactionbff.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
//Princípio OCP - Usando Métodos de Extensão
@Getter
@Setter
@ToString
@ApiModel(value = "RequisicaoTransacaoDTO", description = "Objeto de transporte para o envio de uma promessa de transação")
public class RequisicaoTransacaoDTO extends TransactionDTO {

    @JsonIgnore
    private SituacaoEnum situacao;
    @JsonIgnore
    private LocalDateTime data;

}
