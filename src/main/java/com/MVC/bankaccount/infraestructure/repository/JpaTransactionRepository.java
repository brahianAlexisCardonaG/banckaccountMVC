package com.MVC.bankaccount.infraestructure.repository;

import com.MVC.bankaccount.infraestructure.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity,Long> {
}
