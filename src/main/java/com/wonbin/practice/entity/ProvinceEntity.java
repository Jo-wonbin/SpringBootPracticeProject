package com.wonbin.practice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "province_table")
public class ProvinceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "provinceEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DistrictEntity> districtEntityList;

//    @OneToMany(mappedBy = "provinceEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<MemberEntity> memberEntityList;
}
