package com.e_bank.service;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.enums.NetworkType;
import com.e_bank.exception.CardNotFoundException;
import com.e_bank.mapper.CardMapper;
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
//    public List<Card> getAll(){
//        return cardRepository.findAll();
//    }
    public List<CardDto> getAllByAccount(Long id){
        return cardMapper.toDtos(cardRepository.findAllByAccount_Id(id));
    }
    public CardDto save(CardDto cardDto){
        var card = cardMapper.toCard(cardDto);
        if (card.getNetwork().equals(NetworkType.VISA)){
            do {
                card.setNumber(Long.valueOf(creditCards().visa().get()));
            } while (cardRepository.findCardByNumberEquals(card.getNumber()));
        } else if (card.getNetwork().equals(NetworkType.MasterCard)){
            do {
                card.setNumber(Long.valueOf(creditCards().masterCard().get()));
            } while (cardRepository.findCardByNumberEquals(card.getNumber()));
        }
        card.setCvv(Integer.valueOf(cvvs().get()));
        card.setExpirationDate(Date.valueOf(LocalDate.now().plusYears(3)));
        card.setIsActivated(true);
        card.setIsBlocked(false);
        return cardMapper.toDto(cardRepository.save(card));
    }
    public CardDto getById(Long id){
        return cardMapper.toDto(cardRepository.findById(id).orElseThrow(CardNotFoundException::new));
    }
    public CardDto delete(Long id){
        var card = cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
        cardRepository.delete(card);
        return cardMapper.toDto(card);
    }
    public CardDto changeCardStatus(CardStatusDto cardDto, Long id){
        var card = cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
        var cardUpdated = cardMapper.changeCardStatus(cardDto, card);
        return cardMapper.toDto(cardRepository.save(cardUpdated));
    }
    public CardDto blockCard(CardBlockingDto cardDto, Long id){
        var card = cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
        var cardUpdated = cardMapper.blockCard(cardDto, card);
        cardUpdated.setIsActivated(false);
        return cardMapper.toDto(cardRepository.save(cardUpdated));
    }
}
