package com.e_bank.controller;

import com.e_bank.dto.TransactionDto;
import com.e_bank.exception.*;
import com.e_bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("internal-transaction/{creditId}&{debitId}")
    public ResponseEntity<?> internalTransaction(@RequestBody TransactionDto transactionDto, @PathVariable Long creditId, @PathVariable Long debitId){
        try {
            var transactions = transactionService.internalTransaction(transactionDto, creditId, debitId);
            return ResponseEntity.status(HttpStatus.CREATED).body(transactions);
        } catch (InsufficientBalanceException | AccountIsClosedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    @PostMapping("external-transaction/{accountId}&{beneficiaryId}")
    public ResponseEntity<?> externalTransaction(@RequestBody TransactionDto transactionDto, @PathVariable Long accountId, @PathVariable Long beneficiaryId){
        try {
            var transaction = transactionService.externalTransaction(transactionDto, accountId, beneficiaryId);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (InsufficientBalanceException | AccountIsClosedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException | BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    @PostMapping("card-transaction/{cardId}&{beneficiaryId}")
    public ResponseEntity<?> cardTransaction(@RequestBody TransactionDto transactionDto, @PathVariable Long cardId, @PathVariable Long beneficiaryId){
        try {
           var card = transactionService.cardTransaction(transactionDto, cardId, beneficiaryId);
            return ResponseEntity.status(HttpStatus.CREATED).body(card);
        } catch (InsufficientBalanceException | AccountIsClosedException | CardIsBlockedException | CardDeactivatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException | BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("get-transactions-by-account/{id}")
    public ResponseEntity<?> getAllByAccount(@PathVariable Long id){
        try {
            var transactions = transactionService.getAllByAccount(id);
            return ResponseEntity.ok(transactions);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            var transaction = transactionService.getById(id);
            return ResponseEntity.ok(transaction);
        } catch (TransactionNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
