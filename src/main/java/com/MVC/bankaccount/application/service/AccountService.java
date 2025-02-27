package com.MVC.bankaccount.application.service;

import com.MVC.bankaccount.application.mapper.account.AccountMapper;
import com.MVC.bankaccount.application.usecase.AccountGetCaseImpl;
import com.MVC.bankaccount.application.usecase.ClientCreateCaseImpl;
import com.MVC.bankaccount.domain.exception.GeneralNotFoundException;
import com.MVC.bankaccount.infrastructure.controller.account.request.AccountClientRequest;
import com.MVC.bankaccount.infrastructure.controller.account.response.AccountClientTransactionResponse;
import com.MVC.bankaccount.infrastructure.entities.AccountEntity;
import com.MVC.bankaccount.infrastructure.entities.ClientEntity;
import com.MVC.bankaccount.infrastructure.repository.JpaAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final JpaAccountRepository jpaAccountRepository;

    private final ClientCreateCaseImpl clientUserCase;

    private final AccountGetCaseImpl accountGetCase;
    private final MessageService messageService;

    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(JpaAccountRepository jpaAccountRepository, ClientCreateCaseImpl clientUserCase, AccountGetCaseImpl accountGetCase, MessageService messageService) {
        this.jpaAccountRepository = jpaAccountRepository;
        this.clientUserCase = clientUserCase;
        this.accountGetCase = accountGetCase;
        this.messageService = messageService;
        this.accountMapper = AccountMapper.INSTANCE;
    }

    public void save(AccountClientRequest accountClientRequest) {
        if (!jpaAccountRepository.findByAccountNumber(accountClientRequest.getAccountNumber()).isEmpty()) {
            throw new GeneralNotFoundException(messageService.getMessage("error.account.code.already.exist", accountClientRequest.getAccountNumber()));
        }

        ClientEntity client = clientUserCase.getOrCreateClient(accountClientRequest.getClient());

        AccountEntity accountEntity = accountMapper.toEntity(accountClientRequest);
        accountEntity.setClient(client);

        jpaAccountRepository.save(accountEntity);
    }

    public List<AccountClientTransactionResponse> getAll() {
        List<AccountEntity> accountEntityList = jpaAccountRepository.findAll();
        return accountGetCase.getAllAccounts(accountEntityList);
    }

    public List<AccountClientTransactionResponse> getProductById(Long id){
        Optional<AccountEntity> accountById = jpaAccountRepository.findById(id);
        if (accountById.isPresent()){
            return accountGetCase.getAccount(accountById);
        }else {
            throw new GeneralNotFoundException(messageService.getMessage("error.account.not-found", id));
        }
    }


    public List<AccountClientTransactionResponse> getByAccountNumber(String accountNumber){
        List<AccountEntity> accountResponse = jpaAccountRepository.findByAccountNumber(accountNumber);
        if (!accountResponse.isEmpty()){
            return accountGetCase.getAllAccounts(accountResponse);
        }else {
            throw new GeneralNotFoundException(messageService.getMessage("error.account.code.not.exist",accountNumber));
        }
    }
}
