package com.MVC.bankaccount.application.service;

import com.MVC.bankaccount.application.usecase.TransactionCreateCaseImpl;
import com.MVC.bankaccount.infrastructure.controller.transaction.request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionCreateCaseImpl transactionCreateCase;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTransaction_CallsCreateTransaction() {
        TransactionRequest transactionRequest = new TransactionRequest();
        String identificationNumber = "123456789";
        String accountNumber = "987654321";

        transactionService.save(transactionRequest, identificationNumber, accountNumber);

        verify(transactionCreateCase, times(1)).createTransaction(transactionRequest, identificationNumber, accountNumber);
    }
}
