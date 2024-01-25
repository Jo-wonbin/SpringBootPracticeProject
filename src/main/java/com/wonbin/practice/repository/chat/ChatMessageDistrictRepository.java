package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatMessageDistrictEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageDistrictRepository extends MongoRepository<ChatMessageDistrictEntity, String> {
    ChatMessageDistrictEntity findTop1ByProvinceIdAndDistrictIdOrderByMessageCreatedTimeDesc(Long provinceId, Long districtId);

    Page<ChatMessageDistrictEntity> findAllByProvinceIdAndDistrictIdOrderByMessageCreatedTimeDesc(Long provinceId, Long districtId, PageRequest pageRequest);
}
