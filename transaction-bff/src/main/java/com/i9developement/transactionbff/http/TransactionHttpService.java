package com.i9developement.transactionbff.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.net.http.HttpClient;

public class TransactionHttpService {
    public static final String ACCEPT = "accept";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2).build();

    private ObjectMapper objectMapper;

    @Value("${app.urlTransaction}")
    private String queryTransaction;

    @Value("${app.urlTransactionById}")
    private String urlTransactionById;

    public TransactionHttpService(final ObjectMapper objectMapper){

        this.objectMapper = objectMapper;

    }
}
