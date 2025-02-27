package com.MVC.bankaccount.application.mapper.account;

import com.MVC.bankaccount.infrastructure.controller.account.request.AccountClientRequest;
import com.MVC.bankaccount.infrastructure.controller.account.response.AccountClientTransactionResponse;
import com.MVC.bankaccount.infrastructure.controller.client.request.ClientRequest;
import com.MVC.bankaccount.infrastructure.controller.transaction.response.TransactionResponse;
import com.MVC.bankaccount.infrastructure.entities.AccountEntity;
import com.MVC.bankaccount.infrastructure.entities.ClientEntity;
import com.MVC.bankaccount.infrastructure.entities.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountClientTransactionResponse toResponse(AccountEntity account);

    ClientRequest toClientRequest(ClientEntity client);

    List<TransactionResponse> toTransactionResponseList(List<TransactionEntity> transactions);

    @Mapping(target = "id", ignore = true)
    AccountEntity toEntity(AccountClientRequest accountClientRequest);
}
