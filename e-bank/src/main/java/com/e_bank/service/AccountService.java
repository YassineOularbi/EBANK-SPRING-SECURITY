package com.e_bank.service;

import com.e_bank.dto.AccountClosingDto;
import com.e_bank.dto.AccountDto;
import com.e_bank.dto.CardBlockingDto;
import com.e_bank.exception.AccountIsClosedException;
import com.e_bank.exception.AccountNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.exception.InsufficientBalanceException;
import com.e_bank.mapper.AccountMapper;
import com.e_bank.model.Account;
import com.e_bank.repository.AccountRepository;
import com.e_bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static net.andreinc.mockneat.unit.financial.IBANs.ibans;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private AccountMapper accountMapper;

//    public List<AccountDto> getAll(){
//        return accountMapper.toAccountDtos(accountRepository.findAll());
//    }
    public List<Account> getAllByUser(Long id) {
    var accounts = accountRepository.getAccountByUser_Id(id);
    if (accounts.isEmpty()) {
        throw new DatabaseEmptyException();
    }
    return accounts;
}

    public AccountDto save(AccountDto accountDto) {
        var account = accountMapper.toAccount(accountDto);
        account.setDate(Date.valueOf(LocalDate.now()));
        account.setIsClosed(false);
        account.setIBAN(ibans().get());
        return accountMapper.toAccountDto(accountRepository.save(account));
    }
    public AccountDto update(AccountDto accountDto, Long id){
        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        var accountUpdated = accountMapper.updateAccountFromDto(accountDto, account);
        return accountMapper.toAccountDto(accountRepository.save(accountUpdated));
    }
    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    public AccountDto delete(Long id) {
        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        accountRepository.delete(account);
        return accountMapper.toAccountDto(account);
    }
    public AccountDto close(AccountClosingDto accountDto, Long id){
        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        if (account.getBalance()<0.00){
            throw new InsufficientBalanceException();
        } else if (account.getIsClosed()) {
            throw new AccountIsClosedException();
        }
        account.setIsClosed(true);
        var accountUpdated = accountMapper.updateAccountFromClosingDto(accountDto, account);
        CardBlockingDto cardBlockingDto = new CardBlockingDto("Account closed");
        cardRepository.findAllByAccount_Id(accountUpdated.getId()).forEach(card -> cardService.blockCard(cardBlockingDto, card.getId()));
        return  accountMapper.toAccountDto(accountRepository.save(accountUpdated));
    }
}
