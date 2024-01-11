package com.wonbin.practice.repository;

import com.wonbin.practice.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Long> {
    Optional<ProvinceEntity> findByName(String provinceName);
}
