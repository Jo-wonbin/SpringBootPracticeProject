package com.wonbin.practice.service;

import com.wonbin.practice.dto.DistrictDto;
import com.wonbin.practice.entity.DistrictEntity;
import com.wonbin.practice.entity.ProvinceEntity;
import com.wonbin.practice.repository.DistrictRepository;
import com.wonbin.practice.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    public List<DistrictDto> findByProvinceId(String provinceName) {
        Optional<ProvinceEntity> optionalProvinceEntity = provinceRepository.findByName(provinceName);
        if (optionalProvinceEntity.isPresent()) {
            ProvinceEntity provinceEntity = optionalProvinceEntity.get();
            List<DistrictEntity> districtEntityList = districtRepository.findAllByProvinceEntity(provinceEntity);

            List<DistrictDto> districtDtoList = new ArrayList<>();
            for (DistrictEntity districtEntity : districtEntityList
            ) {
                districtDtoList.add(DistrictDto.toDistrictDto(districtEntity));
            }
            return districtDtoList;
        } else {
            return null;
        }
    }
}
