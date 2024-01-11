package com.wonbin.practice.service;

import com.wonbin.practice.dto.ProvinceDto;
import com.wonbin.practice.entity.ProvinceEntity;
import com.wonbin.practice.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    public List<ProvinceDto> findAll() {
        List<ProvinceDto> provinceDtoList = new ArrayList<>();
        List<ProvinceEntity> provinceEntityList = provinceRepository.findAll();

        for (ProvinceEntity provinceEntity : provinceEntityList) {
            provinceDtoList.add(ProvinceDto.toProvinceDto(provinceEntity));
        }
        return provinceDtoList;
    }
}
