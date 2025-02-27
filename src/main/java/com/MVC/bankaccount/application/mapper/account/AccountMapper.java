package com.MVC.bankaccount.application.mapper.account;

import com.MVC.bankaccount.infraestructure.controller.account.request.AccountClientRequest;
import com.MVC.bankaccount.infraestructure.controller.account.response.AccountClientTransactionResponse;
import com.MVC.bankaccount.infraestructure.entities.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountEntity toAccount(AccountClientRequest accountClientRequest);
    AccountClientTransactionResponse toAccountResponse(AccountEntity accountEntity);
}
