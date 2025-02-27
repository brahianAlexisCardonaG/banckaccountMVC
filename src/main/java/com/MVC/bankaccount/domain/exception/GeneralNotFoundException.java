package com.MVC.bankaccount.domain.exception;

public class GeneralNotFoundException extends RuntimeException {
    public GeneralNotFoundException(String message) {
        super(message);
    }
}
