package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.application.mapper.transaction.TransactionMapper;
import com.MVC.bankaccount.application.service.MessageService;
import com.MVC.bankaccount.domain.exception.GeneralNotFoundException;
import com.MVC.bankaccount.infrastructure.controller.transaction.request.TransactionRequest;
import com.MVC.bankaccount.infrastructure.entities.AccountEntity;
import com.MVC.bankaccount.infrastructure.entities.ClientEntity;
import com.MVC.bankaccount.infrastructure.entities.TransactionEntity;
import com.MVC.bankaccount.infrastructure.repository.JpaAccountRepository;
import com.MVC.bankaccount.infrastructure.repository.JpaClientRepository;
import com.MVC.bankaccount.infrastructure.repository.JpaTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionCreateCaseImplTest {

    @Mock
    private JpaTransactionRepository jpaTransactionRepository;

    @Mock
    private JpaClientRepository jpaClientRepository;

    @Mock
    private JpaAccountRepository jpaAccountRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private TransactionCreateCaseImpl transactionCreateCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction_WhenAccountAndClientExist_ShouldCreateTransaction() {
        String identificationNumber = "123456789";
        String accountNumber = "ACC123";

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new BigDecimal("50.00"));

        AccountEntity account = new AccountEntity();
        account.setAccountNumber(accountNumber);
        account.setBalance(new BigDecimal("100.00"));

        ClientEntity client = new ClientEntity();
        client.setIdentificationNumber(identificationNumber);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAccount(account);

        when(jpaAccountRepository.findByAccountNumber(accountNumber)).thenReturn(List.of(account));
        when(jpaClientRepository.findByIdentificationNumber(identificationNumber)).thenReturn(Optional.of(client));
        when(jpaTransactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);

        TransactionEntity result = transactionCreateCaseImpl.createTransaction(transactionRequest, identificationNumber, accountNumber);

        assertNotNull(result);
        assertEquals(account, result.getAccount());
        assertEquals(new BigDecimal("50.00"), account.getBalance()); // Verificamos que se haya actualizado el saldo

        verify(jpaAccountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(jpaClientRepository, times(1)).findByIdentificationNumber(identificationNumber);
        verify(jpaAccountRepository, times(1)).save(account);
        verify(jpaTransactionRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    void testCreateTransaction_WhenAccountOrClientDoesNotExist_ShouldThrowException() {
        String identificationNumber = "123456789";
        String accountNumber = "ACC123";

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new BigDecimal("50.00"));

        when(jpaAccountRepository.findByAccountNumber(accountNumber)).thenReturn(List.of());
        when(jpaClientRepository.findByIdentificationNumber(identificationNumber)).thenReturn(Optional.empty());
        when(messageService.getMessage("error.account.code.client.code.not.exist")).thenReturn("Account or Client does not exist");

        GeneralNotFoundException exception = assertThrows(GeneralNotFoundException.class, () ->
                transactionCreateCaseImpl.createTransaction(transactionRequest, identificationNumber, accountNumber)
        );

        assertEquals("Account or Client does not exist", exception.getMessage());

        verify(jpaAccountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(jpaClientRepository, times(1)).findByIdentificationNumber(identificationNumber);
        verify(jpaTransactionRepository, never()).save(any(TransactionEntity.class));
    }

    @Test
    void testCreateTransaction_WhenInsufficientBalance_ShouldThrowException() {
        String identificationNumber = "123456789";
        String accountNumber = "ACC123";

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new BigDecimal("150.00"));

        AccountEntity account = new AccountEntity();
        account.setAccountNumber(accountNumber);
        account.setBalance(new BigDecimal("100.00"));

        ClientEntity client = new ClientEntity();
        client.setIdentificationNumber(identificationNumber);

        when(jpaAccountRepository.findByAccountNumber(accountNumber)).thenReturn(List.of(account));
        when(jpaClientRepository.findByIdentificationNumber(identificationNumber)).thenReturn(Optional.of(client));
        when(messageService.getMessage("error.account.insufficient.balance")).thenReturn("Insufficient balance");

        GeneralNotFoundException exception = assertThrows(GeneralNotFoundException.class, () ->
                transactionCreateCaseImpl.createTransaction(transactionRequest, identificationNumber, accountNumber)
        );

        assertEquals("Insufficient balance", exception.getMessage());

        verify(jpaAccountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(jpaClientRepository, times(1)).findByIdentificationNumber(identificationNumber);
        verify(jpaTransactionRepository, never()).save(any(TransactionEntity.class));
    }
}