package com.wonbin.practice.entity.restaurant;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "new_address")
    private String newAddress;

    @Column(name = "address")
    private String address;

    @Column(name = "food_category")
    private String foodCategory;

    @Column(name = "food")
    private String food;

    @Column(name = "phone_number")
    private String phoneNumber;


}
