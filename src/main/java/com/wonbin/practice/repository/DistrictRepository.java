package com.wonbin.practice.repository;

import com.wonbin.practice.entity.board.DistrictEntity;
import com.wonbin.practice.entity.board.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
    List<DistrictEntity> findAllByProvinceEntity(ProvinceEntity provinceEntity);

}
