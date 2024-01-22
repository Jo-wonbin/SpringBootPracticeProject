package com.wonbin.practice.repository.chat;

import com.wonbin.practice.entity.chat.ChatRoomDistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomDistrictRepository extends JpaRepository<ChatRoomDistrictEntity, Long> {
    ChatRoomDistrictEntity findByProvinceIdAndDistrictId(Long provinceId, Long districtId);
}
