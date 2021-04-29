package com.i9developement.transactionsvc.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class BeneficiatioDto implements Serializable {

    private static final long serialVersionUID = 2806421543985360625L;

    @ApiModelProperty(value = "CPF do Beneficiário", required = true, example = "00337786583")
    private Long CPF;
    @ApiModelProperty(value = "Código do Banco do Beneficiário", required = true, example = "341")
    private Long codigoBanco;
    @ApiModelProperty(value = "Código da Agência do Beneficiário", required = true, example = "07649")
    private String agencia;
    @ApiModelProperty(value = "Número da Conta do Beneficiário", required = true, example = "07649")
    private String conta;
    @ApiModelProperty(value = "Nome do favorecido do Beneficiário", required = true, example = "João da Silva")
    private String nomeFavorecido;

}
