package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatMessageEntity;
import com.wonbin.practice.entity.chat.ChatMessageOneToOneEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageOneToOneRepository extends MongoRepository<ChatMessageOneToOneEntity, String> {

//    ChatMessageEntity findByChatRoomId(String chatRoomId);

    ChatMessageOneToOneEntity findTop1ByChatRoomIdOrderByMessageCreatedTimeDesc(String chatRoomId);
}
