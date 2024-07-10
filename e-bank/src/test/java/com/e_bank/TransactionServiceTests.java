package com.e_bank;

import com.e_bank.dto.TransactionDto;
import com.e_bank.exception.*;
import com.e_bank.mapper.TransactionMapper;
import com.e_bank.model.Account;
import com.e_bank.model.Beneficiary;
import com.e_bank.model.Card;
import com.e_bank.model.Transaction;
import com.e_bank.repository.AccountRepository;
import com.e_bank.repository.BeneficiaryRepository;
import com.e_bank.repository.CardRepository;
import com.e_bank.repository.TransactionRepository;
import com.e_bank.service.TransactionService;
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
 * Test class for the TransactionService.
 */
public class TransactionServiceTests {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BeneficiaryRepository beneficiaryRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    /**
     * Sets up the mock objects before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the success scenario of retrieving a transaction by ID.
     */
    @Test
    public void testGetById_Success() {
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(transactionMapper.toDto(transaction)).thenReturn(new TransactionDto());

        TransactionDto result = transactionService.getById(transactionId);

        assertNotNull(result);
    }

    /**
     * Tests the scenario where a transaction is not found by ID during retrieval.
     */
    @Test
    public void testGetById_NotFound() {
        Long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.getById(transactionId));
    }

    /**
     * Tests the success scenario of retrieving all transactions by account ID.
     */
    @Test
    public void testGetAllByAccount_Success() {
        Long accountId = 1L;
        when(transactionRepository.findAllByAccount_Id(accountId)).thenReturn(Collections.singletonList(new Transaction()));
        when(transactionMapper.toDtos(anyList())).thenReturn(Collections.singletonList(new TransactionDto()));

        var result = transactionService.getAllByAccount(accountId);

        assertFalse(result.isEmpty());
    }

    /**
     * Tests the success scenario of performing an internal transaction.
     */
//    @Test
//    public void testInternalTransaction_Success() {
//        Long creditId = 1L;
//        Long debitId = 2L;
//        TransactionDto transactionDto = new TransactionDto();
//        Account creditAccount = new Account();
//        creditAccount.setBalance(1000.0);
//        Account debitAccount = new Account();
//        Transaction creditTransaction = new Transaction();
//        Transaction debitTransaction = new Transaction();
//
//        when(accountRepository.findById(creditId)).thenReturn(Optional.of(creditAccount));
//        when(accountRepository.findById(debitId)).thenReturn(Optional.of(debitAccount));
//        when(transactionMapper.toTransaction(transactionDto)).thenReturn(new Transaction());
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(creditTransaction, debitTransaction);
//        when(transactionMapper.toDtos(anyList())).thenReturn(Collections.singletonList(new TransactionDto()));
//
//        var result = transactionService.internalTransaction(transactionDto, creditId, debitId);
//
//        assertFalse(result.isEmpty());
//        verify(accountRepository, times(1)).save(creditAccount);
//        verify(accountRepository, times(1)).save(debitAccount);
//    }

    /**
     * Tests the scenario where an internal transaction fails due to insufficient balance.
     */
//    @Test
//    public void testInternalTransaction_InsufficientBalance() {
//        Long creditId = 1L;
//        Long debitId = 2L;
//        TransactionDto transactionDto = new TransactionDto();
//        transactionDto.setAmount(1000.0);
//        Account creditAccount = new Account();
//        creditAccount.setBalance(500.0);
//
//        when(accountRepository.findById(creditId)).thenReturn(Optional.of(creditAccount));
//        when(accountRepository.findById(debitId)).thenReturn(Optional.of(new Account()));
//
//        assertThrows(InsufficientBalanceException.class, () -> transactionService.internalTransaction(transactionDto, creditId, debitId));
//    }

    /**
     * Tests the success scenario of performing an external transaction.
     */
//    @Test
//    public void testExternalTransaction_Success() {
//        Long accountId = 1L;
//        Long beneficiaryId = 1L;
//        TransactionDto transactionDto = new TransactionDto();
//        Account account = new Account();
//        account.setBalance(1000.0);
//        Beneficiary beneficiary = new Beneficiary();
//        Transaction transaction = new Transaction();
//
//        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
//        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(beneficiary));
//        when(transactionMapper.toTransaction(transactionDto)).thenReturn(transaction);
//        when(transactionRepository.save(transaction)).thenReturn(transaction);
//        when(transactionMapper.toDto(transaction)).thenReturn(new TransactionDto());
//
//        var result = transactionService.externalTransaction(transactionDto, accountId, beneficiaryId);
//
//        assertNotNull(result);
//        verify(accountRepository, times(1)).save(account);
//    }

    /**
     * Tests the scenario where an external transaction fails due to insufficient balance.
     */
//    @Test
//    public void testExternalTransaction_InsufficientBalance() {
//        Long accountId = 1L;
//        Long beneficiaryId = 1L;
//        TransactionDto transactionDto = new TransactionDto();
//        transactionDto.setAmount(1000.0);
//        Account account = new Account();
//        account.setBalance(500.0);
//
//        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
//        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(new Beneficiary()));
//
//        assertThrows(InsufficientBalanceException.class, () -> transactionService.externalTransaction(transactionDto, accountId, beneficiaryId));
//    }

    /**
     * Tests the success scenario of performing a card transaction.
     */
//    @Test
//    public void testCardTransaction_Success() {
//        Long cardId = 1L;
//        Long beneficiaryId = 1L;
//        TransactionDto transactionDto = new TransactionDto();
//        Account account = new Account();
//        account.setBalance(1000.0);
//        Card card = new Card();
//        card.setAccount(account);
//        Beneficiary beneficiary = new Beneficiary();
//        Transaction transaction = new Transaction();
//
//        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
//        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(beneficiary));
//        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
//        when(transactionMapper.toTransaction(transactionDto)).thenReturn(transaction);
//        when(transactionRepository.save(transaction)).thenReturn(transaction);
//        when(transactionMapper.toDto(transaction)).thenReturn(new TransactionDto());
//
//        var result = transactionService.cardTransaction(transactionDto, cardId, beneficiaryId);
//
//        assertNotNull(result);
//        verify(accountRepository, times(1)).save(account);
//    }

    /**
     * Tests the scenario where a card transaction fails due to insufficient balance.
     */
//    @Test
//    public void testCardTransaction_InsufficientBalance() {
//        Long cardId = 1L;
//        Long beneficiaryId = 1L;
//        TransactionDto transactionDto = new TransactionDto();
//        transactionDto.setAmount(1000.0);
//        Account account = new Account();
//        account.setBalance(500.0);
//        Card card = new Card();
//        card.setAccount(account);
//
//        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
//        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(new Beneficiary()));
//
//        assertThrows(InsufficientBalanceException.class, () -> transactionService.cardTransaction(transactionDto, cardId, beneficiaryId));
//    }

    /**
     * Tests the scenario where a card transaction fails due to the card being blocked.
     */
//    @Test
//    public void testCardTransaction_CardIsBlocked() {
//        Long cardId = 1L;
//        Long beneficiaryId = 1L;
//        TransactionDto transactionDto = new TransactionDto();
//        Account account = new Account();
//        account.setBalance(1000.0);
//        Card card = new Card();
//        card.setAccount(account);
//        card.setIsBlocked(true);
//
//        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
//        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(new Beneficiary()));
//
//        assertThrows(CardIsBlockedException.class, () -> transactionService.cardTransaction(transactionDto, cardId, beneficiaryId));
//    }

    /**
     * Tests the scenario where a card transaction fails due to the card being deactivated.
     */
//    @Test
//    public void testCardTransaction_CardDeactivated() {
//        Long cardId = 1L;
//        Long beneficiaryId = 1L;
//        TransactionDto transactionDto = new TransactionDto();
//        Account account = new Account();
//        account.setBalance(1000.0);
//        Card card = new Card();
//        card.setAccount(account);
//        card.setIsActivated(false);
//
//        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
//        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(new Beneficiary()));
//
//        assertThrows(CardDeactivatedException.class, () -> transactionService.cardTransaction(transactionDto, cardId, beneficiaryId));
//    }

}
