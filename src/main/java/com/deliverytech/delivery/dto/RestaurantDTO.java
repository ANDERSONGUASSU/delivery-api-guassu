package com.deliverytech.delivery.dto;

import lombok.Data;

@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private String description;
    private String phone;
    private String address;
    private String addressNumber;

    public RestaurantDTO(Long id, String name, String description, String phone, String address, String addressNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.address = address;
        this.addressNumber = addressNumber;
    }

    public RestaurantDTO() {
        super();
    }

    @Override
    public String toString() {
        return "RestaurantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                '}';
    }
}

