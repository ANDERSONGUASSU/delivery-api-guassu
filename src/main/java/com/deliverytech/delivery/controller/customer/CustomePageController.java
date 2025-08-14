package com.deliverytech.delivery.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deliverytech.delivery.dto.CustomerDto;
import com.deliverytech.delivery.service.CustomerService;

@Controller
@RequestMapping("/customers.html")
public class CustomePageController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.findAll();
    }

}

