package com.deliverytech.delivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.deliverytech.delivery.dto.RestaurantDto;
import com.deliverytech.delivery.model.Restaurant;

@Mapper(componentModel = "spring")
public interface IRestaurantMapper {

    // Map Restaurant to RestaurantDto
    RestaurantDto toDto(Restaurant restaurant);

    // Map RestaurantDto to Restaurant
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Restaurant toEntity(RestaurantDto dto);
}
