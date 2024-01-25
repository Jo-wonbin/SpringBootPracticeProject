package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatMessageProvinceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageProvinceRepository extends MongoRepository<ChatMessageProvinceEntity, String> {
    ChatMessageProvinceEntity findTop1ByProvinceIdOrderByMessageCreatedTimeDesc(Long provinceId);

    Page<ChatMessageProvinceEntity> findAllByProvinceIdOrderByMessageCreatedTimeDesc(Long provinceId, PageRequest pageRequest);
}
