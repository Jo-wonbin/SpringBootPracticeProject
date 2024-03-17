package com.wonbin.practice.dto;

import com.wonbin.practice.entity.restaurant.RestaurantEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RestaurantDto {

    private Long id;
    private String restaurantName;
    private String newAddress;
    private String address;
    private String foodCategory;
    private String food;
    private String phoneNumber;

    public static RestaurantDto changeDto(RestaurantEntity restaurantEntity) {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurantEntity.getId());
        restaurantDto.setRestaurantName(restaurantEntity.getRestaurantName());
        restaurantDto.setNewAddress(restaurantEntity.getNewAddress().trim().equals("") ? "--" : restaurantEntity.getNewAddress());
        restaurantDto.setAddress(restaurantEntity.getAddress());
        restaurantDto.setFoodCategory(restaurantEntity.getFoodCategory().trim().equals("") ? "--" : restaurantEntity.getFoodCategory());
        restaurantDto.setFood(restaurantEntity.getFood().trim().equals("") ? "--" : restaurantEntity.getFood());
        restaurantDto.setPhoneNumber(restaurantEntity.getPhoneNumber().trim().equals("") ? "--" : restaurantEntity.getPhoneNumber().trim());

        return restaurantDto;
    }
}
