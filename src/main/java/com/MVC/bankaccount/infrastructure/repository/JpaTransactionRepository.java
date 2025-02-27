package com.MVC.bankaccount.infrastructure.repository;

import com.MVC.bankaccount.infrastructure.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity,Long> {
}
