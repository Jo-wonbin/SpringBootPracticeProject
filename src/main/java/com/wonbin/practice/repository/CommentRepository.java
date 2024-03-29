package com.wonbin.practice.repository;

import com.wonbin.practice.entity.board.BoardEntity;
import com.wonbin.practice.entity.board.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // select * from comment where board_id = ? order by desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

    @Query(value = "select c.boardEntity from CommentEntity c where c.id=:id")
    BoardEntity findBoardIdByCommentId(Long id);

    Page<CommentEntity> findByBoardEntity(BoardEntity boardEntity, PageRequest pageRequest);
}
