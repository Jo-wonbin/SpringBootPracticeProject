package com.wonbin.practice.controller;

import com.wonbin.practice.dto.DistrictDto;
import com.wonbin.practice.dto.ProvinceDto;
import com.wonbin.practice.service.DistrictService;
import com.wonbin.practice.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/area")
public class AreaController {

    private final ProvinceService provinceService;
    private final DistrictService districtService;

    @GetMapping("/province")
    public ResponseEntity findProvince() {
        List<ProvinceDto> provinceDtoList = provinceService.findAll();
        if (provinceDtoList != null) {
            return new ResponseEntity<>(provinceDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 광역시가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/district/{id}")
    public ResponseEntity findDistrict(@PathVariable Long id) {
        List<DistrictDto> districtDtoList = districtService.findByProvinceId(id);

        if (districtDtoList != null) {
            return new ResponseEntity<>(districtDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 지역구가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
