package com.i9development.transactionbff.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.i9development.transactionbff.events.entity.valueObject.SituacaoEnum;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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

