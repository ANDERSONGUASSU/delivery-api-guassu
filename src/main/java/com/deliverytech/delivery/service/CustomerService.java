package com.deliverytech.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dto.CustomerDto;
import com.deliverytech.delivery.mapper.ICustomerMapper;
import com.deliverytech.delivery.model.Customer;
import com.deliverytech.delivery.repository.ICustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private ICustomerRepository repository;

    @Autowired
    private ICustomerMapper mapper;


    // Find all customers
    public List<CustomerDto> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    // Find customer by ID
    public CustomerDto findById(Long id) {
        return repository.findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // Create a new customer
    public CustomerDto create(CustomerDto dto) {
        Customer entity = mapper.toEntity(dto);
        entity.setPassword(entity.getPassword());
        entity.setActive(true);
        return mapper.toDto(repository.save(entity));
    }

    // Update an existing customer
    public CustomerDto update(Long id, CustomerDto dto) {
        Customer entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Update entity fields
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setCPF(dto.getCPF());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setAddressNumber(dto.getAddressNumber());
        entity.setComplement(dto.getComplement());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setPassword(dto.getPassword());

        return mapper.toDto(repository.save(entity));
    }

    // Delete a customer
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        repository.deleteById(id);
    }

}
