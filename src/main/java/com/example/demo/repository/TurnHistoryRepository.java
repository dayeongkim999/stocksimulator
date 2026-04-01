package com.example.demo.repository;

import com.example.demo.entity.TurnHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnHistoryRepository extends JpaRepository<TurnHistory, Long> {
}