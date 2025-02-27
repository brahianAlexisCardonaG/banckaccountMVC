package com.MVC.bankaccount.application.mapper.client;

import com.MVC.bankaccount.infrastructure.controller.client.request.ClientRequest;
import com.MVC.bankaccount.infrastructure.entities.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientEntity toEntity(ClientRequest clientRequest);
}
