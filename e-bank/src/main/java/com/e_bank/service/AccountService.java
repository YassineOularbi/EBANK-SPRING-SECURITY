package com.e_bank.service;

import com.e_bank.dto.AccountClosingDto;
import com.e_bank.dto.AccountDto;
import com.e_bank.exception.AccountNotFoundException;
import com.e_bank.mapper.AccountMapper;
import com.e_bank.repository.AccountRepository;
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
    private AccountMapper accountMapper;

//    public List<AccountDto> getAll(){
//        return accountMapper.toAccountDtos(accountRepository.findAll());
//    }
    public List<AccountDto> getAllByUser(Long id){
        return accountMapper.toAccountDtos(accountRepository.getAccountByUser_Id(id));
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
    public AccountDto getById(Long id) {
        return accountMapper.toAccountDto(accountRepository.findById(id).orElseThrow(AccountNotFoundException::new));
    }

    public AccountDto delete(Long id) {
        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        accountRepository.delete(account);
        return accountMapper.toAccountDto(account);
    }
    public AccountDto close(AccountClosingDto accountDto, Long id){
        var account = accountRepository.findByIdAndBalanceEquals(id, 0.00);
        var accountUpdated = accountMapper.updateAccountFromClosingDto(accountDto, account);
        accountUpdated.setIsClosed(true);
        return  accountMapper.toAccountDto(accountRepository.save(accountUpdated));
    }
}
