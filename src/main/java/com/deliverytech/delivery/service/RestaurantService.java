package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.RestaurantDTO;
import java.util.List;

public interface RestaurantService {
    List<RestaurantDTO> findAll();
}
