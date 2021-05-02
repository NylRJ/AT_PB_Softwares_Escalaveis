package com.i9developement.transactionbff.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class ErrorDetails {
    private long timestamp;
    private String message;
    private String details;


}