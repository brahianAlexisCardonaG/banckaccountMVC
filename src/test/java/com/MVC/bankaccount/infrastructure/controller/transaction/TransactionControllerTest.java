package com.MVC.bankaccount.infrastructure.controller.transaction;

import com.MVC.bankaccount.application.service.TransactionService;
import com.MVC.bankaccount.infrastructure.controller.transaction.request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTransaction_ShouldReturnSuccessMessage() {
        TransactionRequest transactionRequest = new TransactionRequest();
        String identificationNumber = "123456789";
        String accountNumber = "ACC123";

        ResponseEntity<String> response = transactionController.save(transactionRequest, identificationNumber, accountNumber);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Transacción exitosa", response.getBody());

        verify(transactionService, times(1)).save(transactionRequest, identificationNumber, accountNumber);
    }
}
