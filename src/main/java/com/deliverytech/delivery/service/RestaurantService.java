package com.deliverytech.delivery.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dto.RestaurantDto;
import com.deliverytech.delivery.mapper.IRestaurantMapper;
import com.deliverytech.delivery.model.Restaurant;
import com.deliverytech.delivery.repository.IRestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private IRestaurantRepository repository;

    @Autowired
    private IRestaurantMapper mapper;

    // List all restaurants
    public List<RestaurantDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    // Find by ID
    public RestaurantDto findById(Long id) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return mapper.toDto(restaurant);
    }

    // Create new restaurant
    public RestaurantDto create(RestaurantDto dto) {
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        
        Restaurant entity = mapper.toEntity(dto);
        entity.setActive(true);

        return mapper.toDto(repository.save(entity));
    }

    // Update existing restaurant
    public RestaurantDto update(Long id, RestaurantDto dto) {
        Restaurant entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Atualiza campos
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setCnpj(dto.getCnpj());
        entity.setDescription(dto.getDescription());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setAddressNumber(dto.getAddressNumber());
        entity.setComplement(dto.getComplement());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDto(repository.save(entity));
    }

    // Delete restaurant
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Restaurant not found");
        }
        repository.deleteById(id);
    }
}
