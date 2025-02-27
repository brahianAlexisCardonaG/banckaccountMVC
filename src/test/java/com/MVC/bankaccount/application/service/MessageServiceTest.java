package com.MVC.bankaccount.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageService messageService;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(messageSource);
    }

    @Test
    void testGetMessage_ReturnsExpectedMessage() {
        String key = "error.account.not-found";
        String expectedMessage = "Account not found!";
        Object[] args = new Object[]{"12345"};

        when(messageSource.getMessage(eq(key), eq(args), eq(Locale.getDefault())))
                .thenReturn(expectedMessage);

        String actualMessage = messageService.getMessage(key, args);

        assertEquals(expectedMessage, actualMessage);
        verify(messageSource, times(1)).getMessage(eq(key), eq(args), eq(Locale.getDefault()));
    }
}