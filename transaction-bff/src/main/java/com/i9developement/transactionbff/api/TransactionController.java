package com.i9developement.transactionbff.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1")
public class TransactionController {
    @Value("${app.timeout}")
    private int timeout;
    @Value("${app.retries}")
    public int numberRetries;

}
