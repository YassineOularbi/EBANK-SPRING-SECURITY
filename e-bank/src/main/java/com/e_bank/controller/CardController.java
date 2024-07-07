package com.e_bank.controller;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.exception.CardIsBlockedException;
import com.e_bank.exception.CardNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/get-cards-by-account/{id}")
    public ResponseEntity<?> getAllByAccount(@PathVariable Long id) {
        try {
            var cards = cardService.getAllByAccount(id);
            return ResponseEntity.ok(cards);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            var card = cardService.getById(id);
            return ResponseEntity.ok(card);
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CardDto> save(@RequestBody CardDto cardDto) {
        var savedCard = cardService.save(cardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            var deletedCard = cardService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedCard);
        } catch (CardNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/activate-deactivate/{id}")
    public ResponseEntity<?> changeCardStatus(@RequestBody CardStatusDto cardDto, @PathVariable Long id) {
        try {
            var updatedCard = cardService.changeCardStatus(cardDto, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCard);
        } catch (CardIsBlockedException | CardNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<?> blockCard(@RequestBody CardBlockingDto cardDto, @PathVariable Long id) {
        try {
            var blockedCard = cardService.blockCard(cardDto, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(blockedCard);
        } catch (CardIsBlockedException | CardNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}