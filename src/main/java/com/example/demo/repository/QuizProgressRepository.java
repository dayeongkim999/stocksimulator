
package com.example.demo.repository;

import com.example.demo.entity.QuizProgress;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizProgressRepository extends JpaRepository<QuizProgress, Long> {
    List<QuizProgress> findByUserOrderByStageNumberAsc(User user);
    Optional<QuizProgress> findByUserAndStageNumber(User user, Integer stageNumber);
}