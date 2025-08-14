package com.deliverytech.delivery.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    private String email;
    private String password;
    private String name;
    private String cnpj;
    private String description;
    private String phone;
    private String address;
    private Integer addressNumber;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;
}


