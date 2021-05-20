package com.i9developement.limitessvc.business;

import com.i9developement.limitessvc.domain.dto.BeneficiatioDto;
import com.i9developement.limitessvc.domain.dto.TransactionDTO;
import com.i9developement.limitessvc.domain.models.Conta;
import com.i9developement.limitessvc.domain.models.LimiteDiario;
import com.i9developement.limitessvc.domain.valueObject.SituacaoEnum;
import com.i9developement.limitessvc.exception.DomainBusinessException;
import com.i9developement.limitessvc.repositories.LimiteDiarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = {LimiteBusiness.class})
@TestPropertySource(properties = {"app.topic=transaction"})
public class LimiteBusinessTest {

    @MockBean
    @Autowired
    private LimiteDiarioRepository limiteDiarioRepository;

    @MockBean
    private KafkaSender kafkaSender;

    @Autowired
    private LimiteBusiness limiteBusiness;

    private UUID uuid;


    @BeforeAll
    public static void  warmUp(){
        System.out.println("Inicializando os Testes. ");
    }

    @AfterEach void tearDown(){
        uuid = null;
        System.out.println("Finalizando os Testes. ");
    }

    @AfterTestClass
    void tearDownClass(){
        System.out.println("Finalizando a Classe. ");
    }

    @BeforeTestClass
    public void BeforeTestClass(){
        System.out.println("Inicializando a Classe. ");
    }

    @BeforeEach
    public void setUp(){
        uuid = UUID.randomUUID();
    }


    @Test
    public void testLimitDiario() {
        var dataMock = getDataMock();
//        dataMock.setValor(new BigDecimal(1000));
        var dataMockLd = getLimiteDataMock();
        Mockito.when(limiteDiarioRepository.findByAgenciaAndContaAndData(dataMock.getConta().getCodigoConta(),dataMock.getConta().getCodigoConta(), LocalDate.now())).thenReturn(dataMockLd);
        assertEquals(dataMockLd.getValor(), new BigDecimal(1000));

    }

    @Test
    public void testLimitDiario2() {
        var dataMock = getDataMock();
//        dataMock.setValor(new BigDecimal(1000));
        var dataMockLd = getLimiteDataMock();
        String message;
        Mockito.when(limiteBusiness.limiteDiario(dataMock)).thenReturn(dataMock);

        assertEquals(dataMock.getSituacao(), SituacaoEnum.ANALISADA);

    }

    private TransactionDTO getDataMock() {


        var transactionDTO = new TransactionDTO();
        transactionDTO.setData(LocalDateTime.now());
        Conta conta = new Conta();
        conta.setCodigoAgencia(1210l);
        conta.setCodigoConta(1242l);
        transactionDTO.setConta(conta);
        transactionDTO.setUui(uuid);
        transactionDTO.naoAnalisada();
        var beneficiario = new BeneficiatioDto();
        beneficiario.setAgencia("1219");
        beneficiario.setCodigoBanco(1212l);
        beneficiario.setCPF(12240181l);
        beneficiario.setNomeFavorecido("Favorecido");
        transactionDTO.setBeneficiario(beneficiario);
        transactionDTO.setValor(new BigDecimal(900));

        return transactionDTO;

    }
    private LimiteDiario getLimiteDataMock() {


        var limiteDiarioDTO = new LimiteDiario();
        limiteDiarioDTO.setId(1L);
        limiteDiarioDTO.setAgencia(2356L);
        limiteDiarioDTO.setConta(3255L);
        limiteDiarioDTO.setData(LocalDate.now());
        limiteDiarioDTO.setValor(new BigDecimal(1000.00));
        return limiteDiarioDTO;

    }


}
