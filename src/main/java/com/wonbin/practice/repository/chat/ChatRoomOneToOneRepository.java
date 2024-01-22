package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatRoomOneToOneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomOneToOneRepository extends JpaRepository<ChatRoomOneToOneEntity, Long> {
    List<ChatRoomOneToOneEntity> findByMemberEntityFirstIdOrMemberEntitySecondId(Long memberEntityFirstId, Long memberEntitySecondId);
}
