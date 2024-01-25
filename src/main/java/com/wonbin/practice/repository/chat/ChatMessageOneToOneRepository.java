package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatMessageOneToOneEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageOneToOneRepository extends MongoRepository<ChatMessageOneToOneEntity, String> {

//    ChatMessageEntity findByChatRoomId(String chatRoomId);

    ChatMessageOneToOneEntity findTop1ByChatRoomIdOrderByMessageCreatedTimeDesc(String chatRoomId);

    Page<ChatMessageOneToOneEntity> findAllByChatRoomIdOrderByMessageCreatedTimeDesc(String chatRoomId, PageRequest pageRequest);
}
