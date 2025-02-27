package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.application.mapper.client.ClientMapper;
import com.MVC.bankaccount.infrastructure.controller.client.request.ClientRequest;
import com.MVC.bankaccount.infrastructure.entities.ClientEntity;
import com.MVC.bankaccount.infrastructure.repository.JpaClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientCreateCaseImpl {

    private final JpaClientRepository jpaClientRepository;

    private final ClientMapper clientMapper;

    @Autowired
    public ClientCreateCaseImpl(JpaClientRepository jpaClientRepository) {
        this.jpaClientRepository = jpaClientRepository;
        this.clientMapper = ClientMapper.INSTANCE;
    }

    public ClientEntity getOrCreateClient(ClientRequest clientRequest) {
        return jpaClientRepository.findByIdentificationNumber(clientRequest.getIdentificationNumber())
                .orElseGet(() -> jpaClientRepository.save(clientMapper.toEntity(clientRequest)));
    }
}
