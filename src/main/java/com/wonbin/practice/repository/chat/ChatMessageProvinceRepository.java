package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatMessageProvinceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageProvinceRepository extends MongoRepository<ChatMessageProvinceEntity, String> {
    ChatMessageProvinceEntity findTop1ByProvinceIdOrderByMessageCreatedTimeDesc(Long provinceId);
}
