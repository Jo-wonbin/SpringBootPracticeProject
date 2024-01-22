package com.wonbin.practice.repository;

import com.wonbin.practice.entity.board.BoardEntity;
import com.wonbin.practice.entity.board.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

    void deleteAllByBoardEntity(BoardEntity board);

    void deleteByStoredFileName(String nextToken);

    List<BoardFileEntity> findAllByBoardEntity(BoardEntity boardEntity);
}
