package com.e_bank.controller;

import com.e_bank.dto.CardDto;
import com.e_bank.mapper.CardMapper;
import com.e_bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private CardService cardService;
    @PostMapping("add")
    public ResponseEntity<CardDto> save(CardDto cardDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(cardDto));
    }
}
