package com.MVC.bankaccount.application.service;

import com.MVC.bankaccount.application.usecase.TransactionCreateCaseImpl;
import com.MVC.bankaccount.infrastructure.controller.transaction.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionCreateCaseImpl transactionCreateCase;

    @Autowired
    public TransactionService(TransactionCreateCaseImpl transactionCreateCase) {
        this.transactionCreateCase = transactionCreateCase;
    }

    public void save(TransactionRequest transaction, String identificationNumber, String accountNumber){
        transactionCreateCase.createTransaction(transaction,identificationNumber,accountNumber);
    }
}
