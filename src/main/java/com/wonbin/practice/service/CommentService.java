package com.wonbin.practice.service;

import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.entity.BoardEntity;
import com.wonbin.practice.entity.CommentEntity;
import com.wonbin.practice.repository.BoardRepository;
import com.wonbin.practice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDto commentDto) {
        // 부모 엔티티 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveComment(commentDto, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDto> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);

        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            CommentDto commentDto = CommentDto.toChangeCommentDto(commentEntity, boardId);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

    public Long delete(Long id) {

        BoardEntity boardEntity = commentRepository.findBoardIdByCommentId(id);

        if (boardEntity != null) {
            commentRepository.deleteById(id);
            return boardEntity.getId();
        } else {
            return 0L;
        }

    }

    public Long update(CommentDto commentDto) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toUpdateComment(commentDto, boardEntity);

            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }
}
