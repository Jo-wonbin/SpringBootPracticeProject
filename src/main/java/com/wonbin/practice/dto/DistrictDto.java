package com.wonbin.practice.dto;

import com.wonbin.practice.entity.board.DistrictEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DistrictDto {

    private Long id;
    private String name;

    public static DistrictDto toDistrictDto(DistrictEntity districtEntity) {
        DistrictDto districtDto = new DistrictDto();
        districtDto.setId(districtEntity.getId());
        districtDto.setName(districtEntity.getName());

        return districtDto;
    }
}
