package com.wonbin.practice.service;

import com.wonbin.practice.dto.DistrictDto;
import com.wonbin.practice.entity.DistrictEntity;
import com.wonbin.practice.entity.ProvinceEntity;
import com.wonbin.practice.repository.DistrictRepository;
import com.wonbin.practice.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    public List<DistrictDto> findByProvinceName(String provinceName) {
//        Optional<ProvinceEntity> optionalProvinceEntity = provinceRepository.findByName(provinceName);
        Set<ProvinceEntity> setProvinceEntity = provinceRepository.findByName(provinceName);
        if (!setProvinceEntity.isEmpty()) {
            Iterator<ProvinceEntity> provinceEntityIterator = setProvinceEntity.iterator();

            if (provinceEntityIterator.hasNext()) {
                ProvinceEntity provinceEntity = provinceEntityIterator.next();
                List<DistrictEntity> districtEntityList = districtRepository.findAllByProvinceEntity(provinceEntity);

                List<DistrictDto> districtDtoList = new ArrayList<>();
                for (DistrictEntity districtEntity : districtEntityList) {
                    districtDtoList.add(DistrictDto.toDistrictDto(districtEntity));
                }
                return districtDtoList;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<DistrictDto> findByProvinceId(Long provinceId) {
        Optional<ProvinceEntity> optionalProvinceEntity = provinceRepository.findById(provinceId);
        if (optionalProvinceEntity.isPresent()) {
            List<DistrictEntity> districtEntityList = districtRepository.findAllByProvinceEntity(optionalProvinceEntity.get());
            List<DistrictDto> districtDtoList = new ArrayList<>();
            for (DistrictEntity districtEntity : districtEntityList) {
                districtDtoList.add(DistrictDto.toDistrictDto(districtEntity));
            }
            return districtDtoList;
        } else
            return null;
    }
}
