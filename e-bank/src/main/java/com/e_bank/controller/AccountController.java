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

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;
//    @GetMapping("get-all")
//    public ResponseEntity<List<AccountDto>> getAll(){
//        return ResponseEntity.ok(accountService.getAll());
//    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            var account = accountService.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(account);
        } catch (AccountNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/get-all-by-user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable Long id) {
        try {
            var accounts = accountService.getAllByUser(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(accounts);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("add")
    public ResponseEntity<AccountDto> save(@RequestBody AccountDto accountDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(accountDto));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<AccountDto> update(@RequestBody AccountDto accountDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.update(accountDto, id));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<AccountDto> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.delete(id));
    }
    @PutMapping("/close-account/{id}")
    public ResponseEntity<?> close(@PathVariable Long id, @RequestBody AccountClosingDto accountDto) {
        try {
            var account = accountService.close(accountDto, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(account);
        } catch (InsufficientBalanceException | AccountIsClosedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
