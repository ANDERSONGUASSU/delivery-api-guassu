package com.deliverytech.delivery.service;
 
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dto.RestaurantDTO;
import com.deliverytech.delivery.model.Restaurant;
import com.deliverytech.delivery.repository.IRestaurantRepository;
 
 
@Service
public class RestaurantService {
    @Autowired
    private IRestaurantRepository repository;

    public RestaurantService(IRestaurantRepository repository) {
        this.repository = repository;
    }
 
    public RestaurantService() {
        super();
    }
   
    public List<RestaurantDTO> findAll() {
        return repository.findAll().stream().map(this::ConvertEntityToDTO).collect(Collectors.toList());
    }

    private RestaurantDTO ConvertEntityToDTO(Restaurant entity) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName(entity.getName());
        dto.setDescription((entity.getDescription()));
        return dto;
    }
}
 
 
