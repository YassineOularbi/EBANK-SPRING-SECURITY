package com.e_bank.repository;

import com.e_bank.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByAccount_Id(Long id);
    Boolean findCardByNumberEquals(Long number);
}
