package com.e_bank.service;

import com.e_bank.dto.TransactionDto;
import com.e_bank.enums.TransactionContext;
import com.e_bank.enums.TransactionMethod;
import com.e_bank.enums.TransactionType;
import com.e_bank.exception.*;
import com.e_bank.mapper.TransactionMapper;
import com.e_bank.model.Transaction;
import com.e_bank.repository.AccountRepository;
import com.e_bank.repository.BeneficiaryRepository;
import com.e_bank.repository.CardRepository;
import com.e_bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for managing transactions in the E-Bank application.
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    /**
     * Retrieves a transaction by ID.
     *
     * @param id ID of the transaction to retrieve
     * @return TransactionDto
     * @throws TransactionNotFoundException if the transaction with given ID is not found
     */
    public TransactionDto getById(Long id){
        var transaction = transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);
        return transactionMapper.toDto(transaction);
    }

    /**
     * Retrieves all transactions associated with a specific account.
     *
     * @param id ID of the account
     * @return List of TransactionDto
     */
    public List<TransactionDto> getAllByAccount(Long id){
        return transactionMapper.toDtos(transactionRepository.findAllByAccount_Id(id));
    }

    /**
     * Performs an internal transfer between two accounts.
     *
     * @param transactionDto Data of the transaction
     * @param creditId ID of the credit account
     * @param debitId ID of the debit account
     * @return List of TransactionDto representing the credit and debit transactions
     * @throws AccountIsClosedException if either the credit or debit account is closed
     * @throws InsufficientBalanceException if the credit account has insufficient balance
     */
    public List<TransactionDto> internalTransaction(TransactionDto transactionDto, Long creditId, Long debitId) {
        List<Transaction> transactions = new ArrayList<>();
        var creditAccount = accountRepository.findById(creditId).orElseThrow(AccountNotFoundException::new);
        var debitAccount = accountRepository.findById(debitId).orElseThrow(AccountNotFoundException::new);

        if (creditAccount.getIsClosed() || debitAccount.getIsClosed()){
            throw new AccountIsClosedException();
        }
        if (creditAccount.getBalance() < transactionDto.getAmount()) {
            throw new InsufficientBalanceException();
        }

        creditAccount.setBalance(creditAccount.getBalance() - transactionDto.getAmount());
        debitAccount.setBalance(debitAccount.getBalance() + transactionDto.getAmount());

        var creditTransaction = transactionMapper.toTransaction(transactionDto);
        creditTransaction.setType(TransactionType.Credit);
        creditTransaction.setMethod(TransactionMethod.Account_Transfer);
        creditTransaction.setContext(TransactionContext.Internal);
        creditTransaction.setAccount(creditAccount);
        creditTransaction.setInternalAccount(debitAccount);
        creditTransaction.setDate(Date.valueOf(LocalDate.now()));
        creditTransaction.setTime(Time.valueOf(LocalTime.now()));

        var debitTransaction = transactionMapper.toTransaction(transactionDto);
        debitTransaction.setType(TransactionType.Debit);
        debitTransaction.setMethod(TransactionMethod.Account_Transfer);
        debitTransaction.setContext(TransactionContext.Internal);
        debitTransaction.setAccount(debitAccount);
        debitTransaction.setInternalAccount(creditAccount);
        debitTransaction.setDate(Date.valueOf(LocalDate.now()));
        debitTransaction.setTime(Time.valueOf(LocalTime.now()));

        transactions.add(transactionRepository.save(creditTransaction));
        transactions.add(transactionRepository.save(debitTransaction));

        accountRepository.save(creditAccount);
        accountRepository.save(debitAccount);

        return transactionMapper.toDtos(transactions);
    }

    /**
     * Performs an external transaction from an account to a beneficiary.
     *
     * @param transactionDto Data of the transaction
     * @param accountId ID of the account
     * @param beneficiaryId ID of the beneficiary
     * @return TransactionDto representing the external transaction
     * @throws AccountIsClosedException if the account is closed
     * @throws InsufficientBalanceException if the account has insufficient balance
     */
    public TransactionDto externalTransaction(TransactionDto transactionDto, Long accountId, Long beneficiaryId){
        var account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        var beneficiary = beneficiaryRepository.findById(beneficiaryId).orElseThrow(BeneficiaryNotFoundException::new);
        var transaction = transactionMapper.toTransaction(transactionDto);

        if (account.getIsClosed()){
            throw new AccountIsClosedException();
        }
        if (account.getBalance() < transactionDto.getAmount()) {
            throw new InsufficientBalanceException();
        }

        account.setBalance(account.getBalance()-transactionDto.getAmount());
        transaction.setContext(TransactionContext.External);
        transaction.setType(TransactionType.Credit);
        transaction.setDate(Date.valueOf(LocalDate.now()));
        transaction.setTime(Time.valueOf(LocalTime.now()));
        transaction.setAccount(account);
        transaction.setBeneficiary(beneficiary);

        accountRepository.save(account);
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }

    /**
     * Performs a card transaction from an account using a card to a beneficiary.
     *
     * @param transactionDto Data of the transaction
     * @param cardId ID of the card
     * @param beneficiaryId ID of the beneficiary
     * @return TransactionDto representing the card transaction
     * @throws AccountIsClosedException if the account is closed
     * @throws InsufficientBalanceException if the account has insufficient balance
     * @throws CardNotFoundException if the card with given ID is not found
     * @throws CardIsBlockedException if the card is blocked
     * @throws CardDeactivatedException if the card is not activated
     */
    public TransactionDto cardTransaction(TransactionDto transactionDto, Long cardId, Long beneficiaryId){
        var card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
        var beneficiary = beneficiaryRepository.findById(beneficiaryId).orElseThrow(BeneficiaryNotFoundException::new);
        var account = accountRepository.findById(card.getAccount().getId()).orElseThrow(AccountNotFoundException::new);
        var transaction = transactionMapper.toTransaction(transactionDto);

        if (account.getIsClosed()){
            throw new AccountIsClosedException();
        }
        if (account.getBalance() < transactionDto.getAmount()) {
            throw new InsufficientBalanceException();
        }
        if (card.getIsBlocked()){
            throw new CardIsBlockedException();
        }
        if (!card.getIsActivated()){
            throw new CardDeactivatedException();
        }

        account.setBalance(account.getBalance()-transactionDto.getAmount());
        transaction.setContext(TransactionContext.External);
        transaction.setType(TransactionType.Credit);
        transaction.setMethod(TransactionMethod.Card);
        transaction.setDate(Date.valueOf(LocalDate.now()));
        transaction.setTime(Time.valueOf(LocalTime.now()));
        transaction.setAccount(account);
        transaction.setBeneficiary(beneficiary);
        transaction.setCard(card);

        accountRepository.save(account);
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }
}
