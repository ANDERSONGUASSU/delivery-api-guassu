package com.deliverytech.delivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.deliverytech.delivery.dto.CustomerDto;
import com.deliverytech.delivery.model.Customer;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {

    // Map Customer to CustomerDto
    CustomerDto toDto(Customer customer);

    // Map CustomerCreateDto to Customer
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerDto dto);
}
