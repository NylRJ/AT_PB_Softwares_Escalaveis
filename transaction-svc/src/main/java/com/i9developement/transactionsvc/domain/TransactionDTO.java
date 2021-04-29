package com.i9developement.transactionsvc.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "uui")
@ToString
@ApiModel(value = "TransactionDTO", description = "Objeto de transporte para o envio de uma promessa de transação")
public class TransactionDTO implements Serializable {

    private static final long serialVersionUID = 2806421523585360625L;

    @NotNull
    @ApiModelProperty(value = "Valor da transação", required = true)
    private BigDecimal valor;
    @ApiModelProperty(value = "Data da transação", required = true)
    @NotNull
    private LocalDateTime data;
    @NotNull
    private br.com.coffeeandit.transaction.domain.Conta conta;
    @NotNull
    private br.com.coffeeandit.transaction.domain.BeneficiatioDto beneficiario;
    @NotNull
    private br.com.coffeeandit.transaction.domain.TipoTransacao tipoTransacao;
    @ApiModelProperty(value = "Identificador único da transação", required = true)
    private UUID uui;
    @ApiModelProperty(value = "Situação da Transação", required = false)
    @NotNull
    private br.com.coffeeandit.transaction.domain.SituacaoEnum situacao;

    public void naoAnalisada() {
        setSituacao(br.com.coffeeandit.transaction.domain.SituacaoEnum.NAO_ANALISADA);
    }

    public void analisada() {
        setSituacao(br.com.coffeeandit.transaction.domain.SituacaoEnum.ANALISADA);
    }

    public void rejeitada() {
        setSituacao(br.com.coffeeandit.transaction.domain.SituacaoEnum.REJEITADA);
    }

    public void suspeitaFraude() {
        setSituacao(br.com.coffeeandit.transaction.domain.SituacaoEnum.EM_SUSPEITA_FRAUDE);
    }

    public void analiseHumana() {
        setSituacao(br.com.coffeeandit.transaction.domain.SituacaoEnum.EM_ANALISE_HUMANA);
    }

    public void aprovada() {
        setSituacao(br.com.coffeeandit.transaction.domain.SituacaoEnum.APROVADA);
    }

    public boolean isAnalisada() {

        return getSituacao().equals(br.com.coffeeandit.transaction.domain.SituacaoEnum.ANALISADA);
    }
}
