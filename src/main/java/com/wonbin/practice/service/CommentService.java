package com.wonbin.practice.service;

import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.entity.BoardEntity;
import com.wonbin.practice.entity.CommentEntity;
import com.wonbin.practice.entity.MemberEntity;
import com.wonbin.practice.repository.BoardRepository;
import com.wonbin.practice.repository.CommentRepository;
import com.wonbin.practice.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long save(CommentDto commentDto, String email) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(email);
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            // 부모 엔티티 조회
            Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
            if (optionalBoardEntity.isPresent()) {
                BoardEntity boardEntity = optionalBoardEntity.get();
                commentDto.setCommentWriter(memberEntity.getMemberName());
                commentDto.setMemberEmail(memberEntity.getMemberEmail());
                CommentEntity commentEntity = CommentEntity.toSaveComment(commentDto, boardEntity, memberEntity);
                return commentRepository.save(commentEntity).getId();
            } else {
                return 0L;
            }
        } else
            return -1L;
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

    @Transactional
    public Long update(CommentDto commentDto, Long boardId, String email) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            commentDto.setMemberEmail(email);
            CommentEntity commentEntity = CommentEntity.toUpdateComment(commentDto, boardEntity);

            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public CommentDto findById(Long commentId) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentId);
        if (optionalCommentEntity.isPresent()) {
            CommentDto commentDto = CommentDto.toCommentDto(optionalCommentEntity.get());
            return commentDto;
        } else
            return null;
    }

    public Page<CommentDto> findAllByPage(Pageable pageable, Long boardId) {

        int page = pageable.getPageNumber() - 1;
        int pageLimit = 5;

        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            PageRequest pageRequest = PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
            Page<CommentEntity> commentEntityPage = commentRepository.findByBoardEntity(boardEntity, pageRequest);

            return commentEntityPage.map(commentEntity -> CommentDto.toChangeCommentDto(
                    commentEntity, boardId
            ));
        } else
            return null;
    }
}
