package com.MVC.bankaccount.application.service;

import com.MVC.bankaccount.application.mapper.account.AccountMapper;
import com.MVC.bankaccount.application.usecase.AccountGetCaseImpl;
import com.MVC.bankaccount.application.usecase.ClientCreateCaseImpl;
import com.MVC.bankaccount.domain.exception.GeneralNotFoundException;
import com.MVC.bankaccount.infrastructure.controller.account.request.AccountClientRequest;
import com.MVC.bankaccount.infrastructure.controller.account.response.AccountClientTransactionResponse;
import com.MVC.bankaccount.infrastructure.entities.AccountEntity;
import com.MVC.bankaccount.infrastructure.entities.ClientEntity;
import com.MVC.bankaccount.infrastructure.repository.JpaAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private JpaAccountRepository jpaAccountRepository;

    @Mock
    private ClientCreateCaseImpl clientUserCase;

    @Mock
    private AccountGetCaseImpl accountGetCase;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(jpaAccountRepository, clientUserCase, accountGetCase, messageService);
    }

    @Test
    void save_ShouldThrowException_WhenAccountAlreadyExists() {
        AccountClientRequest request = new AccountClientRequest();
        request.setAccountNumber("12345");

        when(jpaAccountRepository.findByAccountNumber("12345")).thenReturn(List.of(new AccountEntity()));
        when(messageService.getMessage("error.account.code.already.exist", "12345")).thenReturn("Account already exists");

        GeneralNotFoundException exception = assertThrows(GeneralNotFoundException.class, () -> accountService.save(request));

        assertEquals("Account already exists", exception.getMessage());
        verify(jpaAccountRepository, times(1)).findByAccountNumber("12345");
    }

    @Test
    void save_ShouldSaveAccount_WhenNewAccount() {
        AccountClientRequest request = new AccountClientRequest();
        request.setAccountNumber("12345");

        ClientEntity client = new ClientEntity();
        AccountEntity accountEntity = AccountMapper.INSTANCE.toEntity(request);
        accountEntity.setClient(client);

        when(jpaAccountRepository.findByAccountNumber("12345")).thenReturn(List.of());
        when(clientUserCase.getOrCreateClient(request.getClient())).thenReturn(client);
        when(jpaAccountRepository.save(any(AccountEntity.class))).thenReturn(accountEntity);

        assertDoesNotThrow(() -> accountService.save(request));
        verify(jpaAccountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    void getAll_ShouldReturnListOfAccounts() {
        List<AccountEntity> accountEntities = List.of(new AccountEntity());
        List<AccountClientTransactionResponse> responses = List.of(new AccountClientTransactionResponse());

        when(jpaAccountRepository.findAll()).thenReturn(accountEntities);
        when(accountGetCase.getAllAccounts(accountEntities)).thenReturn(responses);

        List<AccountClientTransactionResponse> result = accountService.getAll();

        assertEquals(1, result.size());
        verify(jpaAccountRepository, times(1)).findAll();
    }

    @Test
    void getProductById_ShouldReturnAccount_WhenIdExists() {
        AccountEntity accountEntity = new AccountEntity();
        when(jpaAccountRepository.findById(1L)).thenReturn(Optional.of(accountEntity));
        when(accountGetCase.getAccount(Optional.of(accountEntity))).thenReturn(List.of(new AccountClientTransactionResponse()));

        List<AccountClientTransactionResponse> result = accountService.getProductById(1L);

        assertFalse(result.isEmpty());
        verify(jpaAccountRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_ShouldThrowException_WhenIdNotExists() {
        when(jpaAccountRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageService.getMessage("error.account.not-found", 1L)).thenReturn("Account not found");

        GeneralNotFoundException exception = assertThrows(GeneralNotFoundException.class, () -> accountService.getProductById(1L));

        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    void getByAccountNumber_ShouldReturnAccount_WhenNumberExists() {
        List<AccountEntity> accountEntities = List.of(new AccountEntity());
        when(jpaAccountRepository.findByAccountNumber("12345")).thenReturn(accountEntities);
        when(accountGetCase.getAllAccounts(accountEntities)).thenReturn(List.of(new AccountClientTransactionResponse()));

        List<AccountClientTransactionResponse> result = accountService.getByAccountNumber("12345");

        assertFalse(result.isEmpty());
        verify(jpaAccountRepository, times(1)).findByAccountNumber("12345");
    }

    @Test
    void getByAccountNumber_ShouldThrowException_WhenNumberNotExists() {
        when(jpaAccountRepository.findByAccountNumber("12345")).thenReturn(List.of());
        when(messageService.getMessage("error.account.code.not.exist", "12345")).thenReturn("Account does not exist");

        GeneralNotFoundException exception = assertThrows(GeneralNotFoundException.class, () -> accountService.getByAccountNumber("12345"));

        assertEquals("Account does not exist", exception.getMessage());
    }
}
