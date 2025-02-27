package com.MVC.bankaccount.infrastructure.controller.account;

import com.MVC.bankaccount.application.service.AccountService;
import com.MVC.bankaccount.infrastructure.controller.account.request.AccountClientRequest;
import com.MVC.bankaccount.infrastructure.controller.account.response.AccountClientTransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearAccount_ShouldReturnCreatedMessage() {
        AccountClientRequest request = new AccountClientRequest();

        ResponseEntity<String> response = accountController.crearAccount(request);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("La cuenta fue creada con éxito", response.getBody());

        verify(accountService, times(1)).save(request);
    }

    @Test
    void testGetAll_WhenNoParams_ShouldReturnAllAccounts() {
        List<AccountClientTransactionResponse> mockAccounts = Collections.singletonList(new AccountClientTransactionResponse());
        when(accountService.getAll()).thenReturn(mockAccounts);

        List<AccountClientTransactionResponse> result = accountController.getAll(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountService, times(1)).getAll();
    }

    @Test
    void testGetAll_WhenIdProvided_ShouldReturnAccountById() {
        Long id = 1L;
        List<AccountClientTransactionResponse> mockAccounts = Collections.singletonList(new AccountClientTransactionResponse());
        when(accountService.getProductById(id)).thenReturn(mockAccounts);

        List<AccountClientTransactionResponse> result = accountController.getAll(id, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountService, times(1)).getProductById(id);
    }

    @Test
    void testGetAll_WhenAccountNumberProvided_ShouldReturnAccountByAccountNumber() {
        String accountNumber = "ACC123";
        List<AccountClientTransactionResponse> mockAccounts = Collections.singletonList(new AccountClientTransactionResponse());
        when(accountService.getByAccountNumber(accountNumber)).thenReturn(mockAccounts);

        List<AccountClientTransactionResponse> result = accountController.getAll(null, accountNumber);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountService, times(1)).getByAccountNumber(accountNumber);
    }
}