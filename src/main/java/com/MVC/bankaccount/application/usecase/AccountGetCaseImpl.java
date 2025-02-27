package com.MVC.bankaccount.application.usecase;

import com.MVC.bankaccount.infraestructure.controller.account.response.AccountClientTransactionResponse;
import com.MVC.bankaccount.infraestructure.controller.client.request.ClientRequest;
import com.MVC.bankaccount.infraestructure.controller.transaction.response.TransactionResponse;
import com.MVC.bankaccount.infraestructure.entities.AccountEntity;
import com.MVC.bankaccount.infraestructure.repository.JpaAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountGetCaseImpl {

    private final JpaAccountRepository jpaAccountRepository;

    @Autowired
    public AccountGetCaseImpl(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    public List<AccountClientTransactionResponse> getAllAccounts(List<AccountEntity> accountEntityList) {
        return accountEntityList.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public List<AccountClientTransactionResponse> getAccount(Optional<AccountEntity> accountEntityOptional) {
        return accountEntityOptional.map(account -> List.of(convertToResponse(account)))
                .orElseGet(List::of);
    }

    private AccountClientTransactionResponse convertToResponse(AccountEntity account) {
        AccountClientTransactionResponse response = new AccountClientTransactionResponse();
        BeanUtils.copyProperties(account, response);

        ClientRequest clientRequest = new ClientRequest();
        BeanUtils.copyProperties(account.getClient(), clientRequest);
        response.setClient(clientRequest);

        List<TransactionResponse> transactionEntityList = account.getTransactions()
                .stream()
                .map(transaction -> {
                    TransactionResponse transactionResponse = new TransactionResponse();
                    BeanUtils.copyProperties(transaction, transactionResponse);
                    return transactionResponse;
                })
                .collect(Collectors.toList());

        response.setTransactions(transactionEntityList);
        return response;
    }

}
