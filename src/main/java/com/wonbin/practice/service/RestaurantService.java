package com.wonbin.practice.service;

import com.wonbin.practice.dto.RestaurantDto;
import com.wonbin.practice.entity.restaurant.RestaurantEntity;
import com.wonbin.practice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    public List<RestaurantDto> findRestaurant(String provinceName, String districtName) {
        List<RestaurantEntity> restaurantEntityList = restaurantRepository.findByAddressStartingWithAndAddressContaining(provinceName, districtName);

        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        for (RestaurantEntity restaurantEntity : restaurantEntityList){
            restaurantDtoList.add(RestaurantDto.changeDto(restaurantEntity));
        }

        return restaurantDtoList;
    }
}
