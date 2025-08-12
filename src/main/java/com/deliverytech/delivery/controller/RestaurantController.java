package com.deliverytech.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dto.RestaurantDto;
import com.deliverytech.delivery.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.findAll();
    }   

}
