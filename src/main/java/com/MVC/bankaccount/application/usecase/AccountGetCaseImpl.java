package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.application.mapper.account.AccountMapper;
import com.MVC.bankaccount.infraestructure.controller.account.response.AccountClientTransactionResponse;
import com.MVC.bankaccount.infraestructure.entities.AccountEntity;
import com.MVC.bankaccount.infraestructure.repository.JpaAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountGetCaseImpl {

    private final JpaAccountRepository jpaAccountRepository;

    private final AccountMapper accountMapper;

    @Autowired
    public AccountGetCaseImpl(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountMapper = AccountMapper.INSTANCE;
    }

    public List<AccountClientTransactionResponse> getAllAccounts(List<AccountEntity> accountEntityList) {
        return accountEntityList.stream()
                .map(accountMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<AccountClientTransactionResponse> getAccount(Optional<AccountEntity> accountEntityOptional) {
        return accountEntityOptional
                .map(account -> List.of(accountMapper.toResponse(account)))
                .orElseGet(List::of);
    }

}
