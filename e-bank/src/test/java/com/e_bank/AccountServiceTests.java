package com.e_bank;

import com.e_bank.dto.AccountClosingDto;
import com.e_bank.dto.AccountDto;
import com.e_bank.exception.AccountIsClosedException;
import com.e_bank.exception.AccountNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.exception.InsufficientBalanceException;
import com.e_bank.mapper.AccountMapper;
import com.e_bank.model.Account;
import com.e_bank.repository.AccountRepository;
import com.e_bank.repository.CardRepository;
import com.e_bank.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the AccountService.
 */
public class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    /**
     * Sets up the mock objects before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the success scenario of retrieving all accounts by user ID.
     */
    @Test
    public void testGetAllByUser_Success() {
        when(accountRepository.getAccountByUser_Id(1L)).thenReturn(Collections.singletonList(new Account()));

        var result = accountService.getAllByUser(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    /**
     * Tests the scenario where the database is empty when retrieving all accounts by user ID.
     */
    @Test
    public void testGetAllByUser_EmptyDatabase() {
        when(accountRepository.getAccountByUser_Id(1L)).thenReturn(Collections.emptyList());

        assertThrows(DatabaseEmptyException.class, () -> accountService.getAllByUser(1L));
    }

    /**
     * Tests the success scenario of saving an account.
     */
    @Test
    public void testSaveAccount_Success() {
        AccountDto accountDto = new AccountDto();
        Account account = new Account();

        when(accountMapper.toAccount(accountDto)).thenReturn(account);
        when(accountMapper.toAccountDto(account)).thenReturn(accountDto);

        when(accountRepository.save(account)).thenReturn(account);

        var savedAccount = accountService.save(accountDto);

        assertNotNull(savedAccount);
        assertEquals(accountDto.getType(), savedAccount.getType());
    }

    /**
     * Tests the success scenario of updating an account.
     */
    @Test
    public void testUpdateAccount_Success() {
        AccountDto accountDto = new AccountDto();
        Long accountId = 1L;

        Account existingAccount = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));

        Optional<Account> fetchedAccount = Optional.ofNullable(accountService.getById(accountId));
        assertTrue(fetchedAccount.isPresent());

        Account updatedAccount = new Account();
        when(accountMapper.updateAccountFromDto(accountDto, existingAccount)).thenReturn(updatedAccount);
        when(accountMapper.toAccountDto(updatedAccount)).thenReturn(accountDto);

        when(accountRepository.save(updatedAccount)).thenReturn(updatedAccount);

        var updatedDto = accountService.update(accountDto, accountId);

        assertNotNull(updatedDto);
        assertEquals(accountDto.getBalance(), updatedDto.getBalance());
    }

    /**
     * Tests the success scenario of retrieving an account by ID.
     */
    @Test
    public void testGetAccountById_Success() {
        Long accountId = 1L;

        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Optional<Account> fetchedAccount = Optional.ofNullable(accountService.getById(accountId));
        assertTrue(fetchedAccount.isPresent());

        assertNotNull(fetchedAccount);
    }

    /**
     * Tests the scenario where an account is not found by ID.
     */
    @Test
    public void testGetAccountById_NotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getById(accountId));
    }

    /**
     * Tests the success scenario of deleting an account.
     */
    @Test
    public void testDeleteAccount_Success() throws AccountNotFoundException {
        Long accountId = 1L;
        Account account = new Account();
        AccountDto accountDto = new AccountDto();  // Initialize with appropriate values if necessary

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        doNothing().when(accountRepository).delete(account);
        when(accountMapper.toAccountDto(account)).thenReturn(accountDto);

        Optional<Account> fetchAccount = Optional.ofNullable(accountService.getById(accountId));
        assertTrue(fetchAccount.isPresent());

        AccountDto deletedDto = accountService.delete(accountId);

        assertNotNull(deletedDto);
    }

    /**
     * Tests the scenario of deleting a not found account.
     */
    @Test
    public void testDeleteAccount_AccountNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> {
            accountService.delete(accountId);
        });
    }

    /**
     * Tests the success scenario of closing an account.
     */
    @Test
    public void testCloseAccount_Success() throws Exception {
        Long accountId = 1L;
        AccountClosingDto closingDto = new AccountClosingDto();

        Account account = new Account();
        account.setBalance(100.0);
        account.setIsClosed(false);

        AccountDto accountDto = new AccountDto();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.updateAccountFromClosingDto(closingDto, account)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toAccountDto(account)).thenReturn(accountDto);
        when(cardRepository.findAllByAccount_Id(accountId)).thenReturn(Collections.emptyList());

        AccountDto closedAccountDto = accountService.close(closingDto, accountId);

        assertNotNull(closedAccountDto);
        assertTrue(account.getIsClosed());
    }

    /**
     * Tests the scenario where an account cannot be closed due to insufficient balance.
     */
    @Test
    public void testCloseAccount_InsufficientBalance() {
        Long accountId = 1L;
        AccountClosingDto closingDto = new AccountClosingDto();

        Account account = new Account();
        account.setBalance(-10.0);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class, () -> accountService.close(closingDto, accountId));
    }

    /**
     * Tests the scenario where an account is already closed and cannot be closed again.
     */
//    @Test
//    public void testCloseAccount_AccountAlreadyClosed() {
//        Long accountId = 1L;
//
//        Account account = new Account();
//        account.setIsClosed(true);
//        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
//        AccountClosingDto closingDto = accountMapper.toDto(account);
//
//        assertThrows(AccountIsClosedException.class, () -> accountService.close(closingDto, accountId));
//    }
}
