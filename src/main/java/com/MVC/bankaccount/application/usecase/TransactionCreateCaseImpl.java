package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.application.service.MessageService;
import com.MVC.bankaccount.domain.exception.GeneralNotFoundException;
import com.MVC.bankaccount.infraestructure.controller.transaction.request.TransactionRequest;
import com.MVC.bankaccount.infraestructure.entities.AccountEntity;
import com.MVC.bankaccount.infraestructure.entities.ClientEntity;
import com.MVC.bankaccount.infraestructure.entities.TransactionEntity;
import com.MVC.bankaccount.infraestructure.repository.JpaAccountRepository;
import com.MVC.bankaccount.infraestructure.repository.JpaClientRepository;
import com.MVC.bankaccount.infraestructure.repository.JpaTransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionCreateCaseImpl {

    private final JpaTransactionRepository jpaTransactionRepository;

    private final JpaClientRepository jpaClientRepository;

    private final JpaAccountRepository jpaAccountRepository;

    private final AccountGetCaseImpl accountGetCase;

    private final MessageService messageService;

    @Autowired
    public TransactionCreateCaseImpl(JpaTransactionRepository jpaTransactionRepository, JpaClientRepository jpaClientRepository, JpaAccountRepository jpaAccountRepository, AccountGetCaseImpl accountGetCase, MessageService messageService) {
        this.jpaTransactionRepository = jpaTransactionRepository;
        this.jpaClientRepository = jpaClientRepository;
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountGetCase = accountGetCase;
        this.messageService = messageService;
    }

    public TransactionEntity createTransaction(TransactionRequest transaction, String identificationNumber, String accountNumber) {

        List<AccountEntity> existingAccount = jpaAccountRepository.findByAccountNumber(accountNumber);
        Optional<ClientEntity> existingClient = jpaClientRepository.findByIdentificationNumber(identificationNumber);

        if (existingAccount.isEmpty() || existingClient.isEmpty()) {
            throw new GeneralNotFoundException(messageService.getMessage("error.account.code.client.code.not.exist"));
        } else {

            AccountEntity account = existingAccount.get(0);

            if (transaction.getAmount().compareTo(account.getBalance()) > 0) {
                throw new GeneralNotFoundException(messageService.getMessage("error.account.insufficient.balance"));
            }

            TransactionEntity transactionEntity = new TransactionEntity();
            BeanUtils.copyProperties(transaction, transactionEntity);
            transactionEntity.setAccount(account);

            account.setBalance(account.getBalance().subtract(transaction.getAmount()));

            jpaAccountRepository.save(account);

            return jpaTransactionRepository.save(transactionEntity);
        }
    }
}
