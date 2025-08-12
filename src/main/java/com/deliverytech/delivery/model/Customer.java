package com.deliverytech.delivery.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false, unique = true, length = 11)
    private Number CPF;
    @Column(nullable = false, length = 15)
    private String phone;
    @Column(nullable = false, length = 255)
    private String address;
    @Column(nullable = false, length = 100)
    private Number addressNumber;


}
