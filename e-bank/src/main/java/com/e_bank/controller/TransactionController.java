package com.e_bank.controller;

import com.e_bank.dto.TransactionDto;
import com.e_bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("internal-transaction/{creditId}&{debitId}")
    public ResponseEntity<List<TransactionDto>> internalTransaction(@RequestBody TransactionDto transactionDto, @PathVariable Long creditId, @PathVariable Long debitId){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.internalTransaction(transactionDto, creditId, debitId));
    }
    @PostMapping("external-transaction/{accountId}&{beneficiaryId}")
    public ResponseEntity<TransactionDto> externalTransaction(@RequestBody TransactionDto transactionDto, @PathVariable Long accountId, @PathVariable Long beneficiaryId){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.externalTransaction(transactionDto, accountId, beneficiaryId));
    }
    @PostMapping("card-transaction/{cardId}&{beneficiaryId}")
    public ResponseEntity<TransactionDto> cardTransaction(@RequestBody TransactionDto transactionDto, @PathVariable Long cardId, @PathVariable Long beneficiaryId){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.cardTransaction(transactionDto, cardId, beneficiaryId));
    }
    @GetMapping("get-transactions-by-account/{id}")
    public ResponseEntity<List<TransactionDto>> getAllByAccount(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getAllByAccount(id));
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(transactionService.getById(id));
    }
}
