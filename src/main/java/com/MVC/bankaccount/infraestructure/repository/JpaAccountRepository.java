package com.MVC.bankaccount.infraestructure.repository;

import com.MVC.bankaccount.infraestructure.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAccountRepository extends JpaRepository<AccountEntity,Long> {
    List<AccountEntity> findByAccountNumber (String accountNumber);
}
