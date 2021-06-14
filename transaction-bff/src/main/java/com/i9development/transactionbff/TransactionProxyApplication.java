package com.i9development.transactionbff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@SpringBootApplication
@EnableWebFlux
@EnableSwagger2WebFlux
@EnableWebFluxSecurity
public class TransactionProxyApplication {

    public static final Locale LOCALE = new Locale("pt",
            "br");

    public static void main(String[] args) {
        SpringApplication.run(TransactionProxyApplication.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        var timeModule = new JavaTimeModule();
        mapper.registerModule(timeModule);
        timeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(
                DateTimeFormatter.
                        ofPattern("yyyy-MM-dd'T'HH:mm:ss", LOCALE)));
        return mapper;
    }


}
