package com.i9developement.transactionbff.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i9developement.transactionbff.events.dto.TransactionDTO;
import com.i9developement.transactionbff.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
@Service
@Log4j2
public class TransactionHttpService {
    public static final String ACCEPT = "accept";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2).build();

    private ObjectMapper objectMapper;

    @Value("${app.urlTransaction}")
    private String queryTransaction;

    @Value("${app.urlTransactionById}")
    private String urlTransactionById;

    public TransactionHttpService(final ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;

    }

    public Flux<TransactionDTO> queryTransactionBlock(Long conta, Long agencia) {

        return Flux.fromIterable(queryTransaction(agencia, conta)).limitRate(100).cache(Duration.ofSeconds(3));

    }

    private List<TransactionDTO> queryTransaction(@NotNull final Long agencia, @NotNull final Long conta) {

        var urlTransaction = String.format(queryTransaction,agencia, conta);
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlTransaction))
                .setHeader(ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();

        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.OK.value()){
                var transactionDTOS = objectMapper.readValue(response.body(), new TypeReference<List<TransactionDTO>>() {

                });
                if (transactionDTOS.isEmpty()){
                    throw new NotFoundException(String.format("Não foi possivel encontrar dados para agência %s e conta %s", agencia, conta));
                }
                return transactionDTOS;
            }
        }catch (IOException | InterruptedException e){
            log.error(e.getMessage(), e);
        }
        return null;
    }


}
