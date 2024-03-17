package com.wonbin.practice.repository;

import com.wonbin.practice.entity.restaurant.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    List<RestaurantEntity> findByAddressStartingWithAndAddressContaining(String provinceName, String districtName);
}
