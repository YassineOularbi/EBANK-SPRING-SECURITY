package com.e_bank.service;

import com.e_bank.exception.AccountNotFoundException;
import com.e_bank.model.Account;
import com.e_bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    public Account delete(Long id) {
        accountRepository.deleteById(id);
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }
}
