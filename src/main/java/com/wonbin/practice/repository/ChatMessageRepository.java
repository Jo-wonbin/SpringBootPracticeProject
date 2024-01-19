package com.wonbin.practice.repository;

import com.wonbin.practice.entity.ChatMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, Long> {

    Page<ChatMessageEntity> findAllByOrderByMessageCreatedTimeDesc(Pageable pageable);
}
