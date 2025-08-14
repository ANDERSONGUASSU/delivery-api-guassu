package com.deliverytech.delivery.mapper;

import org.mapstruct.Mapper;

import com.deliverytech.delivery.dto.CustomerDto;
import com.deliverytech.delivery.model.Customer;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {

    // Map Customer to CustomerDto
    CustomerDto toDto(Customer customer);

    // Map CustomerCreateDto to Customer
    Customer toEntity(CustomerDto dto);
}
