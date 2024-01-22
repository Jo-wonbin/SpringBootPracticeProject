package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatRoomProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomProvinceRepository extends JpaRepository<ChatRoomProvinceEntity, Long> {
    ChatRoomProvinceEntity findByProvinceId(Long provinceId);
}
