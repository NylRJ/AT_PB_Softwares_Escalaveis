package com.i9development.transactionbff.events.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class Conta implements Serializable {

    private static final long serialVersionUID = 2806412403585360625L;

    @ApiModelProperty(value = "Código da Agência")
    @NotNull(message = "Informar o código da Agência.")
    private Long codigoAgencia;
    @ApiModelProperty(value = "Código da Conta")
    @NotNull(message = "Informar o código da Conta.")
    private Long codigoConta;
}

