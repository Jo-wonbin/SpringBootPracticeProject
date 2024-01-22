package com.wonbin.practice.service;

import com.wonbin.practice.dto.ChatMessageDto;
import com.wonbin.practice.entity.chat.ChatMessageEntity;
import com.wonbin.practice.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public void save(ChatMessageDto chatMessageDto) {
        try {
            chatMessageRepository.save(ChatMessageEntity.toEntity(chatMessageDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ChatMessageDto> getChatHistory(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ChatMessageEntity> chatMessageDtoList = chatMessageRepository.findAllByOrderByMessageCreatedTimeDesc(pageRequest);

        return chatMessageDtoList.stream()
                .map(ChatMessageDto::toDto)
                .collect(Collectors.toList());
    }
}
