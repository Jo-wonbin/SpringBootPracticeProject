package com.wonbin.practice.service;

import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.entity.BoardEntity;
import com.wonbin.practice.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;

    public void save(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
        boardRepository.save(boardEntity);
    }
}
