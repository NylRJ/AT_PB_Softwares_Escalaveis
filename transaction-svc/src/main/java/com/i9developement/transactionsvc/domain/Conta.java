package com.i9developement.transactionsvc.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Conta implements Serializable {

    private static final long serialVersionUID = 2806412403585360625L;
    @ApiModelProperty(value = "Código da Agência", required = true)
    private Long codigoAgencia;
    @ApiModelProperty(value = "Código da Conta Corrente", required = true)
    private Long codigoConta;
}
