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

/**
 * Service layer for managing accounts in the E-Bank application.
 */
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

    /**
     * Retrieves all accounts associated with a user.
     *
     * @param id ID of the user
     * @return List of accounts
     * @throws DatabaseEmptyException if no accounts are found for the user
     */
    public List<Account> getAllByUser(Long id) {
        var accounts = accountRepository.getAccountByUser_Id(id);
        if (accounts.isEmpty()) {
            throw new DatabaseEmptyException();
        }
        return accounts;
    }

    /**
     * Saves a new account.
     *
     * @param accountDto Account data to be saved
     * Generate a IBAN using MockNeat's IBAN generator
     * @return Saved account DTO
     */
    public AccountDto save(AccountDto accountDto) {
        var account = accountMapper.toAccount(accountDto);
        account.setDate(Date.valueOf(LocalDate.now()));
        account.setIsClosed(false);
        account.setIBAN(ibans().get());
        return accountMapper.toAccountDto(accountRepository.save(account));
    }

    /**
     * Updates an existing account.
     *
     * @param accountDto Account data to update
     * @param id         ID of the account to update
     * @return Updated account DTO
     * @throws AccountNotFoundException if the account with given ID is not found
     */
    public AccountDto update(AccountDto accountDto, Long id) {
        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        var accountUpdated = accountMapper.updateAccountFromDto(accountDto, account);
        return accountMapper.toAccountDto(accountRepository.save(accountUpdated));
    }

    /**
     * Retrieves an account by ID.
     *
     * @param id ID of the account to retrieve
     * @return Account entity
     * @throws AccountNotFoundException if the account with given ID is not found
     */
    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    /**
     * Deletes an account by ID.
     *
     * @param id ID of the account to delete
     * @return Deleted account DTO
     * @throws AccountNotFoundException if the account with given ID is not found
     */
    public AccountDto delete(Long id) {
        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        accountRepository.delete(account);
        return accountMapper.toAccountDto(account);
    }

    /**
     * Closes an account.
     *
     * @param accountDto Data related to account closing
     * @param id         ID of the account to close
     * @return Closed account DTO
     * @throws AccountNotFoundException    if the account with given ID is not found
     * @throws InsufficientBalanceException if the account balance is less than zero
     * @throws AccountIsClosedException     if the account is already closed
     */
    public AccountDto close(AccountClosingDto accountDto, Long id) {
        var account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        if (account.getBalance() < 0.00) {
            throw new InsufficientBalanceException();
        } else if (account.getIsClosed()) {
            throw new AccountIsClosedException();
        }
        account.setIsClosed(true);
        var accountUpdated = accountMapper.updateAccountFromClosingDto(accountDto, account);
        CardBlockingDto cardBlockingDto = new CardBlockingDto("Account closed");
        cardRepository.findAllByAccount_Id(accountUpdated.getId()).forEach(card -> cardService.blockCard(cardBlockingDto, card.getId()));
        return accountMapper.toAccountDto(accountRepository.save(accountUpdated));
    }
}
