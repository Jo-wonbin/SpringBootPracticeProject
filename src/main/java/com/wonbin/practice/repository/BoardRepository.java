package com.wonbin.practice.repository;

import com.wonbin.practice.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // update board_table set board_hits = board_hits + 1 where id = ?
    // 엔티티 기준 쿼리
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    List<BoardEntity> findAllByProvinceIdOrderByIdDesc(Long provinceId);

    List<BoardEntity> findAllByDistrictIdOrderByIdDesc(Long districtId);

    @Modifying
    @Query(value = "update BoardEntity b set b.boardTitle = :boardTitle, b.boardContents=:boardContents, b.updatedTime = :boardUpdateTime where b.id=:id")
    Integer updateBoard(@Param("id") Long id, @Param("boardTitle") String boardTitle, @Param("boardContents") String boardContents, @Param("boardUpdateTime") LocalDateTime boardUpdateTime);


    List<BoardEntity> findAllByOrderByIdDesc();
}
