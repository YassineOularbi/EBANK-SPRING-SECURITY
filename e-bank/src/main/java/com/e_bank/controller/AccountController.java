package com.e_bank.controller;

import com.e_bank.dto.AccountClosingDto;
import com.e_bank.dto.AccountDto;
import com.e_bank.exception.AccountIsClosedException;
import com.e_bank.exception.AccountNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.exception.InsufficientBalanceException;
import com.e_bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            var account = accountService.getById(id);
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-by-user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable Long id) {
        try {
            var accounts = accountService.getAllByUser(id);
            return ResponseEntity.ok(accounts);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<AccountDto> save(@RequestBody AccountDto accountDto) {
        var savedAccount = accountService.save(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody AccountDto accountDto, @PathVariable Long id) {
        try {
            var updatedAccount = accountService.update(accountDto, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            var deletedAccount = accountService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/close-account/{id}")
    public ResponseEntity<?> close(@PathVariable Long id, @RequestBody AccountClosingDto accountDto) {
        try {
            var closedAccount = accountService.close(accountDto, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(closedAccount);
        } catch (InsufficientBalanceException | AccountIsClosedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
