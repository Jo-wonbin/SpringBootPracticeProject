package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatMessageDistrictEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageDistrictRepository extends MongoRepository<ChatMessageDistrictEntity, String> {
    ChatMessageDistrictEntity findTop1ByProvinceIdAndDistrictIdOrderByMessageCreatedTimeDesc(Long provinceId, Long districtId);
}
