package com.wonbin.practice.dto;

import com.wonbin.practice.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDto {

    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private Long provinceId;
    private String provinceName;
    private Long districtId;
    private String districtName;


    public static MemberDto toMemberDto(MemberEntity memberEntity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setMemberEmail(memberEntity.getMemberEmail());
        memberDto.setMemberPassword(memberEntity.getMemberPassword());
        memberDto.setMemberName(memberEntity.getMemberName());
        memberDto.setProvinceName(memberEntity.getProvinceName());
        memberDto.setDistrictName(memberEntity.getDistrictName());
        memberDto.setProvinceId(memberEntity.getProvinceId());
        memberDto.setDistrictId(memberEntity.getDistrictId());

        return memberDto;
    }
}
