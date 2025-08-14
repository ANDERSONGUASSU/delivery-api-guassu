package com.deliverytech.delivery.controller.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.deliverytech.delivery.dto.CustomerDto;
import com.deliverytech.delivery.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomePageController {
    @Autowired
    private CustomerService service;

    // GET para exibir lista e formulário
    @GetMapping
    public String showCustomersPage(Model model) {
        model.addAttribute("customers", service.findAll());
        model.addAttribute("customer", new CustomerDto());
        return "customers/page"; // template único
    }

    // POST para criar novo cliente
    @PostMapping
    public String createCustomer(@ModelAttribute("customer") CustomerDto customerDto) {
        service.create(customerDto);
        return "redirect:/customers";
    }

}

