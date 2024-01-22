package com.wonbin.practice.specification;

import com.wonbin.practice.entity.board.BoardEntity;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecification {

    public static Specification<BoardEntity> hasProvinceId(Long provinceId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("provinceId"), provinceId);
    }

    public static Specification<BoardEntity> hasDistrictId(Long districtId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("districtId"), districtId);
    }
}
