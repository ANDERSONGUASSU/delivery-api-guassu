package com.deliverytech.delivery.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private Number CPF;
    private String phone;
    private String address;
    private Number addressNumber;

    public CustomerDto(Long id, String name, String email, Number CPF, String phone, String address, Number addressNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.CPF = CPF;
        this.phone = phone;
        this.address = address;
        this.addressNumber = addressNumber;
    }

    public CustomerDto() {
        super();
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", CPF=" + CPF +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", addressNumber=" + addressNumber +
                '}';
    }
}
