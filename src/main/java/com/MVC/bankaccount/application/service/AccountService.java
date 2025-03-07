package com.MVC.bankaccount.application.service;

import com.MVC.bankaccount.application.usecase.AccountGetCaseImpl;
import com.MVC.bankaccount.application.usecase.ClientCreateCaseImpl;
import com.MVC.bankaccount.domain.exception.GeneralNotFoundException;
import com.MVC.bankaccount.infraestructure.controller.account.request.AccountClientRequest;
import com.MVC.bankaccount.infraestructure.controller.account.response.AccountClientTransactionResponse;
import com.MVC.bankaccount.infraestructure.entities.AccountEntity;
import com.MVC.bankaccount.infraestructure.entities.ClientEntity;
import com.MVC.bankaccount.infraestructure.repository.JpaAccountRepository;
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

    @Autowired
    public AccountService(JpaAccountRepository jpaAccountRepository, ClientCreateCaseImpl clientUserCase, AccountGetCaseImpl accountGetCase, MessageService messageService) {
        this.jpaAccountRepository = jpaAccountRepository;
        this.clientUserCase = clientUserCase;
        this.accountGetCase = accountGetCase;
        this.messageService = messageService;
    }

    public void save(AccountClientRequest accountClientRequest) {
        List<AccountEntity> accountNumber = jpaAccountRepository.findByAccountNumber(accountClientRequest.getAccountNumber());

        if (!accountNumber.isEmpty()) {
            throw new GeneralNotFoundException(messageService.getMessage("error.account.code.already.exist", accountClientRequest.getAccountNumber()));
        }

        ClientEntity client = clientUserCase.getOrCreateClient(accountClientRequest.getClient());
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(accountClientRequest,accountEntity );
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
