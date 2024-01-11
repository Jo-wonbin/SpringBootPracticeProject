package com.wonbin.practice.repository;

import com.wonbin.practice.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Long> {
    Set<ProvinceEntity> findByName(String provinceName);
}
