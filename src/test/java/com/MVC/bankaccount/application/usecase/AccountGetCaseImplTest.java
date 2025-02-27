package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.application.mapper.account.AccountMapper;
import com.MVC.bankaccount.infrastructure.controller.account.response.AccountClientTransactionResponse;
import com.MVC.bankaccount.infrastructure.entities.AccountEntity;
import com.MVC.bankaccount.infrastructure.repository.JpaAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountGetCaseImplTest {

    @Mock
    private JpaAccountRepository jpaAccountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountGetCaseImpl accountGetCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccount_WhenNotPresent_ShouldReturnEmptyList() {
        // Arrange
        Optional<AccountEntity> optionalAccount = Optional.empty();

        // Act
        List<AccountClientTransactionResponse> result = accountGetCaseImpl.getAccount(optionalAccount);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(accountMapper, never()).toResponse(any());
    }
}