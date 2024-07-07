package com.e_bank;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.exception.CardIsBlockedException;
import com.e_bank.exception.CardNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.mapper.CardMapper;
import com.e_bank.model.Card;
import com.e_bank.repository.CardRepository;
import com.e_bank.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the CardService.
 */
public class CardServiceTests {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardService cardService;

    /**
     * Sets up the mock objects before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the success scenario of retrieving all cards by account ID.
     */
    @Test
    public void testGetAllByAccount_Success() {
        when(cardRepository.findAllByAccount_Id(1L)).thenReturn(Collections.singletonList(new Card()));

        var result = cardService.getAllByAccount(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    /**
     * Tests the scenario where the database is empty when retrieving all cards by account ID.
     */
    @Test
    public void testGetAllByAccount_EmptyDatabase() {
        when(cardRepository.findAllByAccount_Id(1L)).thenReturn(Collections.emptyList());

        assertThrows(DatabaseEmptyException.class, () -> cardService.getAllByAccount(1L));
    }

    /**
     * Tests the success scenario of saving a card.
     */
    @Test
    public void testSaveCard_Success() {
        CardDto cardDto = new CardDto();
        Card card = new Card();

        when(cardMapper.toCard(cardDto)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(cardDto);

        when(cardRepository.existsByNumber(anyLong())).thenReturn(false);
        when(cardRepository.save(card)).thenReturn(card);

        assertTrue(card.getIsActivated());
        assertFalse(card.getIsBlocked());

        var savedCard = cardService.save(cardDto);

        assertNotNull(savedCard);
    }

    /**
     * Tests the success scenario of retrieving a card by ID.
     */
    @Test
    public void testGetCardById_Success() {
        Long cardId = 1L;

        Card card = new Card();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        var fetchedCard = cardService.getById(cardId);

        assertNotNull(fetchedCard);
    }

    /**
     * Tests the scenario where a card is not found by ID during retrieval.
     */
    @Test
    public void testGetCardById_NotFound() {
        Long cardId = 1L;

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> cardService.getById(cardId));
    }

    /**
     * Tests the success scenario of deleting a card.
     */
    @Test
    public void testDeleteCard_Success() {
        Long cardId = 1L;

        Card card = new Card();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        var deletedDto = cardService.delete(cardId);

        assertNotNull(deletedDto);
    }

    /**
     * Tests the success scenario of changing a card's status.
     */
    @Test
    public void testChangeCardStatus_Success() {
        Long cardId = 1L;
        CardStatusDto statusDto = new CardStatusDto();

        Card card = new Card();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        when(cardMapper.changeCardStatus(statusDto, card)).thenReturn(card);

        when(cardRepository.save(card)).thenReturn(card);

        var updatedDto = cardService.changeCardStatus(statusDto, cardId);

        assertNotNull(updatedDto);
    }

    /**
     * Tests the success scenario of blocking a card.
     */
    @Test
    public void testBlockCard_Success() {
        Long cardId = 1L;
        CardBlockingDto blockingDto = new CardBlockingDto();

        Card card = new Card();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        when(cardMapper.blockCard(blockingDto, card)).thenReturn(card);

        when(cardRepository.save(card)).thenReturn(card);

        assertTrue(card.getIsActivated());
        assertFalse(card.getIsBlocked());

        var blockedDto = cardService.blockCard(blockingDto, cardId);

        assertNotNull(blockedDto);

    }

    /**
     * Tests the scenario where attempting to block an already blocked card results in an exception.
     */
    @Test
    public void testBlockCard_CardIsAlreadyBlocked() {
        Long cardId = 1L;
        CardBlockingDto blockingDto = new CardBlockingDto();

        Card card = new Card();
        card.setIsBlocked(true);
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        assertThrows(CardIsBlockedException.class, () -> cardService.blockCard(blockingDto, cardId));
    }

}
