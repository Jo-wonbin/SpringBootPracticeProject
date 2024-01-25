package com.wonbin.practice.service;

import com.wonbin.practice.dto.chat.*;
import com.wonbin.practice.entity.chat.*;
import com.wonbin.practice.entity.member.MemberEntity;
import com.wonbin.practice.repository.MemberRepository;
import com.wonbin.practice.repository.chat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomOneToOneRepository chatRoomOneToOneRepository;
    private final ChatMessageOneToOneRepository chatMessageOneToOneRepository;
    private final ChatRoomProvinceRepository chatRoomProvinceRepository;
    private final ChatMessageProvinceRepository chatMessageProvinceRepository;
    private final ChatRoomDistrictRepository chatRoomDistrictRepository;
    private final ChatMessageDistrictRepository chatMessageDistrictRepository;

    public void save(ChatMessageDto chatMessageDto) throws Exception {
        chatMessageRepository.save(ChatMessageEntity.toEntity(chatMessageDto));
    }

    public void saveOneToOneMessage(ChatMessageOneToOneDto chatMessageOneToOneDto) {
        chatMessageOneToOneRepository.save(ChatMessageOneToOneEntity.toChatOneToOneEntity(chatMessageOneToOneDto));
    }

    public void saveProvinceMessage(ChatMessageProvinceDto chatMessageProvinceDto) {
        chatMessageProvinceRepository.save(ChatMessageProvinceEntity.toChatProvinceEntity(chatMessageProvinceDto));
    }

    public void saveDistrictMessage(ChatMessageDistrictDto chatMessageDistrictDto) {
        chatMessageDistrictRepository.save(ChatMessageDistrictEntity.toChatDistrictEntity(chatMessageDistrictDto));
    }

    public List<ChatMessageDto> getChatHistory(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ChatMessageEntity> chatMessageDtoList = chatMessageRepository.findAllByOrderByMessageCreatedTimeDesc(pageRequest);

        return chatMessageDtoList.stream()
                .map(ChatMessageDto::toDto)
                .collect(Collectors.toList());
    }

    public ChatListDto findChatList(String memberEmail) {
        ChatListDto chatListDto;
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {

            Long memberId = byMemberEmail.get().getId();
            /*
                1대1 채팅방 목록
                1. 로그인한 멤버의 1대1 채팅방을 리스트로 불러옴.
                2. 채팅방 마다 최근 채팅 하나를 불러옴.
                3. dto로 변환하여 반환한다.
             */
            chatListDto = new ChatListDto();

            List<ChatRoomOneToOneEntity> chatRoomOneToOneEntityList = chatRoomOneToOneRepository.findByMemberEntityFirstIdOrMemberEntitySecondId(memberId, memberId);
            List<ChatMessageOneToOneDto> chatMessageOneToOneDtoList = new ArrayList<>();
            List<ChatRoomOneToOneDto> chatRoomOneToOneDtoList = new ArrayList<>();
            for (ChatRoomOneToOneEntity chatRoomOneToOneEntity : chatRoomOneToOneEntityList) {

                ChatRoomOneToOneDto chatRoomOneToOneDto = ChatRoomOneToOneDto.toChatRoomOneToOneDto(chatRoomOneToOneEntity, memberId);

                ChatMessageOneToOneEntity chatMessageOneToOneEntity = chatMessageOneToOneRepository
                        .findTop1ByChatRoomIdOrderByMessageCreatedTimeDesc(chatRoomOneToOneEntity.getChatRoomId());
                ChatMessageOneToOneDto chatMessageOneToOneDto = ChatMessageOneToOneDto.toChatMessageOneToOneDto(chatMessageOneToOneEntity);

                chatMessageOneToOneDtoList.add(chatMessageOneToOneDto);
                chatRoomOneToOneDtoList.add(chatRoomOneToOneDto);
            }
            chatListDto.setChatMessageOneToOneDtoList(chatMessageOneToOneDtoList);
            chatListDto.setChatRoomOneToOneDtoList(chatRoomOneToOneDtoList);

            /*
                광역시 채팅방
                1. 로그인한 유저의 광역시 채팅방불러옴.
                2. 채팅방의 가장 최근 채팅 하나 불러옴.
                3. dto로 변환하여 반환
             */
            ChatRoomProvinceEntity chatRoomProvinceEntity = chatRoomProvinceRepository.findByProvinceId(byMemberEmail.get().getProvinceId());
            ChatRoomProvinceDto chatRoomProvinceDto = ChatRoomProvinceDto.toChatRoomProvinceDto(chatRoomProvinceEntity);

            ChatMessageProvinceEntity chatMessageProvinceEntity = chatMessageProvinceRepository.findTop1ByProvinceIdOrderByMessageCreatedTimeDesc(byMemberEmail.get().getProvinceId());
            ChatMessageProvinceDto chatMessageProvinceDto = ChatMessageProvinceDto.toChatMessageProvinceDto(chatMessageProvinceEntity);

            chatListDto.setChatRoomProvinceDto(chatRoomProvinceDto);
            chatListDto.setChatMessageProvinceDto(chatMessageProvinceDto);

            /*
                지역구 채팅방
                1. 로그인한 유저의 지역구 채팅방불러옴.
                2. 채팅방의 가장 최근 채팅 하나 불러옴.
                3. dto로 변환하여 반환
             */

            ChatRoomDistrictEntity chatRoomDistrictEntity = chatRoomDistrictRepository
                    .findByProvinceIdAndDistrictId(byMemberEmail.get().getProvinceId(), byMemberEmail.get().getDistrictId());
            ChatRoomDistrictDto chatRoomDistrictDto = ChatRoomDistrictDto.toChatRoomDistrictDto(chatRoomDistrictEntity);

            ChatMessageDistrictEntity chatMessageDistrictEntity = chatMessageDistrictRepository
                    .findTop1ByProvinceIdAndDistrictIdOrderByMessageCreatedTimeDesc(byMemberEmail.get().getProvinceId(), byMemberEmail.get().getDistrictId());

            ChatMessageDistrictDto chatMessageDistrictDto = ChatMessageDistrictDto.toChatMessageDistrictDto(chatMessageDistrictEntity);

            chatListDto.setChatRoomDistrictDto(chatRoomDistrictDto);
            chatListDto.setChatMessageDistrictDto(chatMessageDistrictDto);
        } else {
            return null;
        }
        return chatListDto;
    }

    /*
        채팅방이 존재하면 반환하고 없으면 생성하여 반환
     */
    public ChatRoomOneToOneDto findOneToOneChatRoom(String email, Long targetId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(email);

        if (optionalMemberEntity.isPresent()) {

            Long myId = optionalMemberEntity.get().getId();
            String chatRoomId = myId > targetId ? targetId + "-" + myId : myId + "-" + targetId;
            ChatRoomOneToOneEntity chatRoomOneToOneEntity = chatRoomOneToOneRepository.findByChatRoomId(chatRoomId);
            if (chatRoomOneToOneEntity != null) {
                return ChatRoomOneToOneDto.toChatRoomOneToOneDto(chatRoomOneToOneEntity, myId);
            } else {
                ChatRoomOneToOneEntity saveEntity = new ChatRoomOneToOneEntity();

                Optional<MemberEntity> optionalMemberEntity1 = memberRepository.findById(targetId);
                if (optionalMemberEntity.isPresent() && optionalMemberEntity1.isPresent()) {
                    MemberEntity myMemberEntity = optionalMemberEntity.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + myId));
                    MemberEntity targetMemberEntity = optionalMemberEntity1.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + targetId));

                    saveEntity.setMemberEntityFirst(myId > targetId ? targetMemberEntity : myMemberEntity);
                    saveEntity.setMemberEntitySecond(myId < targetId ? targetMemberEntity : myMemberEntity);
                }

                saveEntity.setChatRoomId(chatRoomId);
                saveEntity.setChatRoomName(chatRoomId);
                ChatRoomOneToOneEntity save = chatRoomOneToOneRepository.save(saveEntity);
                return ChatRoomOneToOneDto.toChatRoomOneToOneDto(save, myId);
            }

        } else {
            return null;
        }

    }

    public List<ChatMessageOneToOneDto> getChatOneToOneHistory(String chatRoomId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ChatMessageOneToOneEntity> chatMessageOneToOneEntityList = chatMessageOneToOneRepository.findAllByChatRoomIdOrderByMessageCreatedTimeDesc(chatRoomId, pageRequest);

        List<ChatMessageOneToOneDto> chatMessageOneToOneDtoList = new ArrayList<>();
        for (ChatMessageOneToOneEntity chatMessageOneToOneEntity : chatMessageOneToOneEntityList) {
            chatMessageOneToOneDtoList.add(ChatMessageOneToOneDto.toChatMessageOneToOneDto(chatMessageOneToOneEntity));
        }
        return chatMessageOneToOneDtoList;
    }

    public List<ChatMessageProvinceDto> getChatProvinceHistory(Long provinceId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ChatMessageProvinceEntity> chatMessageProvinceEntities = chatMessageProvinceRepository.findAllByProvinceIdOrderByMessageCreatedTimeDesc(provinceId, pageRequest);

        List<ChatMessageProvinceDto> chatMessageProvinceDtoList = new ArrayList<>();
        for (ChatMessageProvinceEntity chatMessageProvinceEntity : chatMessageProvinceEntities) {
            chatMessageProvinceDtoList.add(ChatMessageProvinceDto.toChatMessageProvinceDto(chatMessageProvinceEntity));
        }
        return chatMessageProvinceDtoList;
    }

    public List<ChatMessageDistrictDto> getChatDistrictHistory(Long provinceId, Long districtId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ChatMessageDistrictEntity> chatMessageDistrictEntities = chatMessageDistrictRepository.findAllByProvinceIdAndDistrictIdOrderByMessageCreatedTimeDesc(provinceId, districtId, pageRequest);

        List<ChatMessageDistrictDto> chatMessageDistrictDtoList = new ArrayList<>();
        for (ChatMessageDistrictEntity chatMessageDistrictEntity : chatMessageDistrictEntities) {
            chatMessageDistrictDtoList.add(ChatMessageDistrictDto.toChatMessageDistrictDto(chatMessageDistrictEntity));
        }
        return chatMessageDistrictDtoList;
    }
}
