package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.application.mapper.transaction.TransactionMapper;
import com.MVC.bankaccount.application.service.MessageService;
import com.MVC.bankaccount.domain.exception.GeneralNotFoundException;
import com.MVC.bankaccount.infraestructure.controller.transaction.request.TransactionRequest;
import com.MVC.bankaccount.infraestructure.entities.AccountEntity;
import com.MVC.bankaccount.infraestructure.entities.ClientEntity;
import com.MVC.bankaccount.infraestructure.entities.TransactionEntity;
import com.MVC.bankaccount.infraestructure.repository.JpaAccountRepository;
import com.MVC.bankaccount.infraestructure.repository.JpaClientRepository;
import com.MVC.bankaccount.infraestructure.repository.JpaTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionCreateCaseImpl {

    private final JpaTransactionRepository jpaTransactionRepository;

    private final JpaClientRepository jpaClientRepository;

    private final JpaAccountRepository jpaAccountRepository;

    private final MessageService messageService;

    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionCreateCaseImpl(JpaTransactionRepository jpaTransactionRepository, JpaClientRepository jpaClientRepository, JpaAccountRepository jpaAccountRepository, MessageService messageService) {
        this.jpaTransactionRepository = jpaTransactionRepository;
        this.jpaClientRepository = jpaClientRepository;
        this.jpaAccountRepository = jpaAccountRepository;
        this.messageService = messageService;
        this.transactionMapper = TransactionMapper.INSTANCE;
    }

    public TransactionEntity createTransaction(TransactionRequest transaction, String identificationNumber, String accountNumber) {

        List<AccountEntity> existingAccount = jpaAccountRepository.findByAccountNumber(accountNumber);
        Optional<ClientEntity> existingClient = jpaClientRepository.findByIdentificationNumber(identificationNumber);

        if (existingAccount.isEmpty() || existingClient.isEmpty()) {
            throw new GeneralNotFoundException(messageService.getMessage("error.account.code.client.code.not.exist"));
        }

        AccountEntity account = existingAccount.get(0);

        if (transaction.getAmount().compareTo(account.getBalance()) > 0) {
            throw new GeneralNotFoundException(messageService.getMessage("error.account.insufficient.balance"));
        }

        // Convertir TransactionRequest a TransactionEntity usando el mapper
        TransactionEntity transactionEntity = transactionMapper.toEntity(transaction);
        transactionEntity.setAccount(account);

        // Actualizar saldo de la cuenta
        account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        jpaAccountRepository.save(account);

        return jpaTransactionRepository.save(transactionEntity);
    }
}
