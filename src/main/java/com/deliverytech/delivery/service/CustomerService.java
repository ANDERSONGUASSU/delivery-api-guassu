package com.deliverytech.delivery.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dto.CustomerDto;
import com.deliverytech.delivery.model.Customer;
import com.deliverytech.delivery.repository.ICustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private ICustomerRepository repository;

    public CustomerService(ICustomerRepository repository) {
        this.repository = repository;
    }
    public CustomerService() {
        super();
    }

    public List<CustomerDto> findAll() {
        return repository.findAll().stream().map(this::ConvertEntityToDto).collect(Collectors.toList());
    }

    public CustomerDto ConvertEntityToDto(Customer entity) {
        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setCPF(entity.getCPF());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setAddressNumber(entity.getAddressNumber());
        return dto;
    }

}
