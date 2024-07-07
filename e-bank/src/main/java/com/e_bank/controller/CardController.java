package com.e_bank.controller;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.exception.CardNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private CardService cardService;
    @GetMapping("get-cards-by-account/{id}")
    public ResponseEntity<?> getAllByAccount(@PathVariable Long id){
        try {
            var cards = cardService.getAllByAccount(id);
            return ResponseEntity.ok(cards);
        } catch (DatabaseEmptyException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            var card = cardService.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(card);
        } catch (CardNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("add")
    public ResponseEntity<CardDto> save(@RequestBody CardDto cardDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(cardDto));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<CardDto> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cardService.delete(id));
    }
    @PutMapping("activate-deactivate/{id}")
    public ResponseEntity<CardDto> changeCardStatus(@RequestBody CardStatusDto cardDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cardService.changeCardStatus(cardDto, id));
    }
    @PutMapping("block/{id}")
    public ResponseEntity<CardDto> blocCard(@RequestBody CardBlockingDto cardDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cardService.blockCard(cardDto, id));
    }
}
