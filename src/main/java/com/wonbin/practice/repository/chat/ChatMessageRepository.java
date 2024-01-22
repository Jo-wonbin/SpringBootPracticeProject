package com.wonbin.practice.repository;

import com.wonbin.practice.entity.chat.ChatMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, Long> {

    Page<ChatMessageEntity> findAllByOrderByMessageCreatedTimeDesc(Pageable pageable);
}
