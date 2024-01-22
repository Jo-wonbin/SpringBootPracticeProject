package com.wonbin.practice.entity.member;

import com.wonbin.practice.dto.MemberDto;
import com.wonbin.practice.entity.board.BoardEntity;
import com.wonbin.practice.entity.board.CommentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberEmail;

    @Column(nullable = false)
    private String memberPassword;

    @Column
    private String memberName;

    @Column
    private Long provinceId;

    @Column
    private String provinceName;

    @Column
    private Long districtId;

    @Column
    private String districtName;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static MemberEntity toMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        memberEntity.setProvinceName(memberDto.getProvinceName());
        memberEntity.setDistrictName(memberDto.getDistrictName());
        memberEntity.setProvinceId(memberDto.getProvinceId());
        memberEntity.setDistrictId(memberDto.getDistrictId());

        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        memberEntity.setProvinceName(memberDto.getProvinceName());
        memberEntity.setDistrictName(memberDto.getDistrictName());
        memberEntity.setProvinceId(memberDto.getProvinceId());
        memberEntity.setDistrictId(memberDto.getDistrictId());

        return memberEntity;
    }
}
