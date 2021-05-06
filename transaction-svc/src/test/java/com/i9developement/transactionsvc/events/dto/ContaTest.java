package com.i9developement.transactionsvc.events.dto;

import com.i9developement.transactionsvc.domain.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContaTest {

    private Conta conta;

    @BeforeEach
    public void setUp() {
        this.conta = new Conta();
    }
    @Test
    public void contaTest() {
        conta.setCodigoAgencia(1214L);
        assertThat(conta.getCodigoAgencia(), is(notNullValue()));
    }
}
