package com.example.demo.repository;

import com.example.demo.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    // QUIZ용인지 NEWS용인지 구분해서 목록을 가져오는 기능
    List<Stage> findByStageTypeOrderByStageNumberAsc(String stageType);

    // 특정 스테이지 번호로 하나만 가져오는 기능
    Optional<Stage> findByStageNumber(Integer stageNumber);
}