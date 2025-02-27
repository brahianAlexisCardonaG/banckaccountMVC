package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.application.mapper.client.ClientMapper;
import com.MVC.bankaccount.infrastructure.controller.client.request.ClientRequest;
import com.MVC.bankaccount.infrastructure.entities.ClientEntity;
import com.MVC.bankaccount.infrastructure.repository.JpaClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientCreateCaseImplTest {

    @Mock
    private JpaClientRepository jpaClientRepository;

    @InjectMocks
    private ClientCreateCaseImpl clientCreateCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrCreateClient_WhenClientExists_ShouldReturnExistingClient() {
        // Arrange
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setIdentificationNumber("123456789");

        ClientEntity existingClient = new ClientEntity();
        existingClient.setIdentificationNumber("123456789");

        when(jpaClientRepository.findByIdentificationNumber("123456789"))
                .thenReturn(Optional.of(existingClient));

        ClientEntity result = clientCreateCaseImpl.getOrCreateClient(clientRequest);

        assertNotNull(result);
        assertEquals("123456789", result.getIdentificationNumber());

        verify(jpaClientRepository, times(1)).findByIdentificationNumber("123456789");
        verify(jpaClientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    void testGetOrCreateClient_WhenClientDoesNotExist_ShouldCreateAndReturnNewClient() {

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setIdentificationNumber("987654321");

        ClientEntity newClient = ClientMapper.INSTANCE.toEntity(clientRequest);

        when(jpaClientRepository.findByIdentificationNumber("987654321"))
                .thenReturn(Optional.empty());
        when(jpaClientRepository.save(any(ClientEntity.class))).thenReturn(newClient);

        ClientEntity result = clientCreateCaseImpl.getOrCreateClient(clientRequest);

        assertNotNull(result);
        assertEquals("987654321", result.getIdentificationNumber());

        verify(jpaClientRepository, times(1)).findByIdentificationNumber("987654321");
        verify(jpaClientRepository, times(1)).save(any(ClientEntity.class));
    }
}
