package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.infraestructure.controller.client.request.ClientRequest;
import com.MVC.bankaccount.infraestructure.entities.ClientEntity;
import com.MVC.bankaccount.infraestructure.repository.JpaClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientCreateCaseImpl {

    private final JpaClientRepository jpaClientRepository;

    @Autowired
    public ClientCreateCaseImpl(JpaClientRepository jpaClientRepository) {
        this.jpaClientRepository = jpaClientRepository;
    }

    public ClientEntity getOrCreateClient(ClientRequest clientRequest ) {
        Optional<ClientEntity> client = jpaClientRepository.findByIdentificationNumber(clientRequest.getIdentificationNumber());
        if (client.isEmpty()) {
            ClientEntity newClient = new ClientEntity();
            BeanUtils.copyProperties(clientRequest, newClient);
            return jpaClientRepository.save(newClient);
        } else {
            return client.get();
        }
    }
}
