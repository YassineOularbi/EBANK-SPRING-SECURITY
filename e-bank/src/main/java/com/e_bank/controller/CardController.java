package com.e_bank.controller;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private CardService cardService;
    @GetMapping("get-cards-by-account/{id}")
    public ResponseEntity<List<CardDto>> getAllByAccount(@PathVariable Long id){
        return ResponseEntity.ok(cardService.getAllByAccount(id));
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<CardDto> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(cardService.getById(id));
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
