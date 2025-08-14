package com.deliverytech.delivery.mapper;

import org.mapstruct.Mapper;

import com.deliverytech.delivery.dto.RestaurantDto;
import com.deliverytech.delivery.model.Restaurant;

@Mapper(componentModel = "spring")
public interface IRestaurantMapper {

    // Map Restaurant to RestaurantDto
    RestaurantDto toDto(Restaurant restaurant);

    // Map RestaurantDto to Restaurant
    Restaurant toEntity(RestaurantDto dto);
}
