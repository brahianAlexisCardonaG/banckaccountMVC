package com.MVC.bankaccount.application.mapper.transaction;

import com.MVC.bankaccount.infrastructure.controller.transaction.request.TransactionRequest;
import com.MVC.bankaccount.infrastructure.entities.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "id", ignore = true)
    TransactionEntity toEntity(TransactionRequest transactionRequest);
}
