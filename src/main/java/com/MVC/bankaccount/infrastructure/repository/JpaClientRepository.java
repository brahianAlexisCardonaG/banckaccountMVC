package com.MVC.bankaccount.infrastructure.repository;

import com.MVC.bankaccount.infrastructure.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientEntity,Long> {
    Optional<ClientEntity> findByIdentificationNumber(String identificationNumber);
}
