package com.i9development.transactionbff.events.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class BeneficiatioDto implements Serializable {

    private static final long serialVersionUID = 2806421543985360625L;

    @ApiModelProperty(value = "CPF do Beneficiário")
    @NotNull(message = "Informar o CPF.")
    private Long CPF;
    @NotNull(message = "Informar o código do banco de destino.")
    @ApiModelProperty(value = "Código do banco destino")
    private Long codigoBanco;
    @NotNull(message = "Informar a agência de destino.")
    @ApiModelProperty(value = "Agência de destino")
    private String agencia;
    @NotNull(message = "Informar a conta de destino.")
    @ApiModelProperty(value = "Conta de destino")
    private String conta;
    @NotNull(message = "Informar o nome do Favorecido.")
    @ApiModelProperty(value = "Nome do Favorecido")
    private String nomeFavorecido;


}

