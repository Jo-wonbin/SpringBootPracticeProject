package com.wonbin.practice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "district_table")
public class DistrictEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private ProvinceEntity provinceEntity;

//    @OneToMany(mappedBy = "districtEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<MemberEntity> memberEntityList;
}
