package com.i9developement.transactionsvc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.i9developement.transactionsvc.TransactionServiceApplication;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime data;
    @NotNull
    private Conta conta;
    @NotNull
    private BeneficiatioDto beneficiario;
    @NotNull
    private TipoTransacao tipoTransacao;
    @ApiModelProperty(value = "Identificador único da transação", required = true)
    private UUID uui;
    @ApiModelProperty(value = "Situação da Transação", required = false)
    @NotNull
    private SituacaoEnum situacao;

    public void naoAnalisada() {
        setSituacao(SituacaoEnum.NAO_ANALISADA);
    }

    public void analisada() {
        setSituacao(SituacaoEnum.ANALISADA);
    }

    public void rejeitada() {
        setSituacao(SituacaoEnum.REJEITADA);
    }

    public void suspeitaFraude() {
        setSituacao(SituacaoEnum.EM_SUSPEITA_FRAUDE);
    }

    public void analiseHumana() {
        setSituacao(SituacaoEnum.EM_ANALISE_HUMANA);
    }

    public void aprovada() {
        setSituacao(SituacaoEnum.APROVADA);
    }

    public boolean isAnalisada() {

        return getSituacao().equals(SituacaoEnum.ANALISADA);
    }
}
