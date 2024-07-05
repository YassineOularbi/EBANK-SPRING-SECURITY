package com.e_bank.controller;

import com.e_bank.dto.AccountClosingDto;
import com.e_bank.dto.AccountDto;
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
    @GetMapping("get-all")
    public ResponseEntity<List<AccountDto>> getAll(){
        return ResponseEntity.ok(accountService.getAll());
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<AccountDto> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(accountService.getById(id));
    }
    @GetMapping("get-all-by-user/{id}")
    public ResponseEntity<List<AccountDto>> getAllByUser(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAllByUser(id));
    }
    @PostMapping("add")
    public ResponseEntity<AccountDto> save(AccountDto accountDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(accountDto));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<AccountDto> update(AccountDto accountDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.update(accountDto, id));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<AccountDto> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.delete(id));
    }
    @PutMapping("close-account/{id}")
    public ResponseEntity<AccountDto> close(@RequestBody AccountClosingDto accountDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.close(accountDto, id));
    }
}
