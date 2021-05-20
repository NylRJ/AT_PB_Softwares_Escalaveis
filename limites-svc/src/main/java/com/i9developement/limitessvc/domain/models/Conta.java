package com.i9developement.limitessvc.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Conta implements Serializable {

    private static final long serialVersionUID = 2806412403585360625L;
    private Long codigoAgencia;
    private Long codigoConta;
}
