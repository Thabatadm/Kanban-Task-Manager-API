package com.example.demo.repository;

import com.example.demo.model.Card;
import com.example.demo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CardRepository  extends JpaRepository<Card, Long> {

    List<Card> findByProjectIdOrderByPositionAsc(Long projectId);

    List<Card> findByProjectIdAndStatus(Long projectId, Status status);
}
