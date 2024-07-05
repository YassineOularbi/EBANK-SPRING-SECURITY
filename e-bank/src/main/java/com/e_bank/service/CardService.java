package com.e_bank.service;

import com.e_bank.dto.CardDto;
import com.e_bank.enums.NetworkType;
import com.e_bank.exception.CardNotFoundException;
import com.e_bank.mapper.CardMapper;
import com.e_bank.model.Card;
import com.e_bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static net.andreinc.mockneat.unit.financial.CVVS.cvvs;
import static net.andreinc.mockneat.unit.financial.CreditCards.creditCards;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardMapper cardMapper;
    public List<Card> getAll(){
        return cardRepository.findAll();
    }
    public CardDto save(CardDto cardDto){
        var card = cardMapper.toCard(cardDto);
        if (card.getNetwork().equals(NetworkType.VISA)){
            card.setNumber(Long.valueOf(creditCards().visa().get()));
        } else if (card.getNetwork().equals(NetworkType.MasterCard)){
            card.setNumber(Long.valueOf(creditCards().masterCard().get()));
        }
        card.setCvv(Integer.valueOf(cvvs().get()));
        card.setExpirationDate(Date.valueOf(LocalDate.now().plusYears(10)));
        return cardMapper.toDto(cardRepository.save(card));
    }
    public Card getById(Long id){
        return cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
    }
    public Card delete(Long id){
        cardRepository.deleteById(id);
        return cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
    }
}
