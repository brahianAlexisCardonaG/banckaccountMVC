package com.MVC.bankaccount.infrastructure.repository;

import com.MVC.bankaccount.infrastructure.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaAccountRepository extends JpaRepository<AccountEntity,Long> {
    List<AccountEntity> findByAccountNumber (String accountNumber);
}
