package com.deliverytech.delivery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dto.RestaurantDTO;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Override
    public List<RestaurantDTO> findAll() {
        List<RestaurantDTO> restaurants = new ArrayList<>();

        RestaurantDTO r1 = new RestaurantDTO();
        r1.setId(1L);
        r1.setName("Pizzaria Guassú");
        r1.setAddress("Rua das Árvores, 123");
        r1.setPhoneNumber("(11) 99999-0001");

        RestaurantDTO r2 = new RestaurantDTO();
        r2.setId(2L);
        r2.setName("Churras do Zé");
        r2.setAddress("Av. das Carnes, 456");
        r2.setPhoneNumber("(11) 99999-0002");

        restaurants.add(r1);
        restaurants.add(r2);

        return restaurants;
    }

}
