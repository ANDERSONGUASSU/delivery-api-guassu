package com.deliverytech.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dto.CustomerDto;
import com.deliverytech.delivery.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.findAll();
    }

}
