package com.i9development.transactionbff.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.i9development.transactionbff.events.entity.Conta;
import com.i9development.transactionbff.events.entity.valueObject.SituacaoEnum;
import com.i9development.transactionbff.events.entity.valueObject.TipoTransacao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
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

    @ApiModelProperty(value = "Valor da transação")
    @NotNull(message = "Informar o valor da transação")
    private BigDecimal valor;

    @ApiModelProperty(value = "Data/hora/minuto e segundo da transação")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime data;

    @NotNull(message = "Informar a conta de origem da transação")
    @ApiModelProperty(value = "Conta de origem da transação")
    @Valid
    private Conta conta;

    @NotNull(message = "Informar o beneficiário da transação")
    @ApiModelProperty(value = "Beneficiário da transação")
    @Valid
    private BeneficiatioDto beneficiario;

    @NotNull(message = "Informar o tipo da transação")
    @ApiModelProperty(value = "Tipo de transação")
    private TipoTransacao tipoTransacao;

    @ApiModelProperty(value = "Código de identificação da transação")
    private UUID uui;

    @ApiModelProperty(value = "Situação da transação")
    private SituacaoEnum situacao;


    public void naoAnalisada() {
        setSituacao(SituacaoEnum.NAO_ANALISADA);
    }

    public void analisada() {
        setSituacao(SituacaoEnum.ANALISADA);
    }

    public void suspeitaFraude() {
        setSituacao(SituacaoEnum.EM_SUSPEITA_FRAUDE);
    }

    public void analiseHumana() {
        setSituacao(SituacaoEnum.EM_ANALISE_HUMANA);
    }

}
