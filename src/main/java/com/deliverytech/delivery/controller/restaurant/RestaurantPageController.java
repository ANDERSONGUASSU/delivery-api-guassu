package com.deliverytech.delivery.controller.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.deliverytech.delivery.dto.RestaurantDto;
import com.deliverytech.delivery.service.RestaurantService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantPageController {

    @Autowired
    private RestaurantService restaurantService;

    // GET para exibir lista e formulário
    @GetMapping
    public String showRestaurantsPage(Model model) {
        model.addAttribute("restaurants", restaurantService.findAll());
        model.addAttribute("restaurant", new RestaurantDto());
        return "restaurants/page"; // template único
    }

    // POST para criar novo restaurante
    @PostMapping
    public String createRestaurant(@ModelAttribute("restaurant") RestaurantDto restaurantDto) {
        restaurantService.create(restaurantDto);
        return "redirect:/restaurants"; // redireciona para atualizar a lista
    }
}


