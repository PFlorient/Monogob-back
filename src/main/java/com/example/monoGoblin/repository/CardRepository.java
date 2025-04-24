package com.example.monoGoblin.repository;

import com.example.monoGoblin.model.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query(value = "SELECT * FROM user ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Card findRandomCard();

    @Query(value = "SELECT * FROM user WHERE id NOT IN (:excludedIds) ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Card findRandomCardExcluding(@Param("excludedIds") List<Integer> excludedIds);
}
