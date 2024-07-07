package com.e_bank.service;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.enums.NetworkType;
import com.e_bank.exception.CardIsBlockedException;
import com.e_bank.exception.CardNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
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

/**
 * Service layer for managing cards in the E-Bank application.
 */
@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    /**
     * Retrieves all cards associated with an account.
     *
     * @param id ID of the account
     * @return List of cards
     * @throws DatabaseEmptyException if no cards are found for the account
     */
    public List<Card> getAllByAccount(Long id) {
        var cards = cardRepository.findAllByAccount_Id(id);
        if (cards.isEmpty()) {
            throw new DatabaseEmptyException();
        }
        return cards;
    }

    /**
     * Saves a new card.
     *
     * @param cardDto Card data to be saved
     * Generate a unique card number using MockNeat's credit card generator
     * @return Saved card DTO
     */
    public CardDto save(CardDto cardDto) {
        var card = cardMapper.toCard(cardDto);

        if (card.getNetwork().equals(NetworkType.VISA)) {
            do {
                card.setNumber(Long.valueOf(creditCards().visa().get()));
            } while (cardRepository.existsByNumber(card.getNumber()));
        } else if (card.getNetwork().equals(NetworkType.MasterCard)) {
            do {
                card.setNumber(Long.valueOf(creditCards().masterCard().get()));
            } while (cardRepository.existsByNumber(card.getNumber()));
        }

        card.setCvv(Integer.valueOf(cvvs().get()));
        card.setExpirationDate(Date.valueOf(LocalDate.now().plusYears(3)));
        card.setIsActivated(true);
        card.setIsBlocked(false);

        return cardMapper.toDto(cardRepository.save(card));
    }

    /**
     * Retrieves a card by ID.
     *
     * @param id ID of the card to retrieve
     * @return Card entity
     * @throws CardNotFoundException if the card with given ID is not found
     */
    public Card getById(Long id) {
        return cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
    }

    /**
     * Deletes a card by ID.
     *
     * @param id ID of the card to delete
     * @return Deleted card DTO
     * @throws CardNotFoundException if the card with given ID is not found
     */
    public CardDto delete(Long id) {
        var card = cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
        cardRepository.delete(card);
        return cardMapper.toDto(card);
    }

    /**
     * Changes the status of a card.
     *
     * @param cardDto Data related to card status change
     * @param id      ID of the card to update
     * @return Updated card DTO
     * @throws CardNotFoundException  if the card with given ID is not found
     * @throws CardIsBlockedException if the card is already blocked
     */
    public CardDto changeCardStatus(CardStatusDto cardDto, Long id) {
        var card = cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
        var cardUpdated = cardMapper.changeCardStatus(cardDto, card);
        if (cardUpdated.getIsBlocked()) {
            throw new CardIsBlockedException();
        }
        return cardMapper.toDto(cardRepository.save(cardUpdated));
    }

    /**
     * Blocks a card.
     *
     * @param cardDto Data related to card blocking
     * @param id      ID of the card to block
     * @return Blocked card DTO
     * @throws CardNotFoundException  if the card with given ID is not found
     * @throws CardIsBlockedException if the card is already blocked
     */
    public CardDto blockCard(CardBlockingDto cardDto, Long id) {
        var card = cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
        var cardUpdated = cardMapper.blockCard(cardDto, card);
        if (cardUpdated.getIsBlocked()) {
            throw new CardIsBlockedException();
        }
        cardUpdated.setIsActivated(false);
        cardUpdated.setIsBlocked(true);
        return cardMapper.toDto(cardRepository.save(cardUpdated));
    }
}
