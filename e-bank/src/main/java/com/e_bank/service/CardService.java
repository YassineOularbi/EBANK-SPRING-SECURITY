package com.e_bank.service;

import com.e_bank.exception.CardNotFoundException;
import com.e_bank.model.Card;
import com.e_bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    public List<Card> getAll(){
        return cardRepository.findAll();
    }
    public Card save(Card card){
        return cardRepository.save(card);
    }
    public Card getById(Long id){
        return cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
    }
    public Card delete(Long id){
        cardRepository.deleteById(id);
        return cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
    }
}
