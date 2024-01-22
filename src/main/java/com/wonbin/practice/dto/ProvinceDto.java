package com.wonbin.practice.dto;

import com.wonbin.practice.entity.board.ProvinceEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProvinceDto {

    private Long id;
    private String name;

    public static ProvinceDto toProvinceDto(ProvinceEntity provinceEntity){
        ProvinceDto provinceDto = new ProvinceDto();
        provinceDto.setId(provinceEntity.getId());
        provinceDto.setName(provinceEntity.getName());

        return provinceDto;
    }
}
