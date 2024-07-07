package com.e_bank.service;

import com.e_bank.dto.TransactionDto;
import com.e_bank.enums.TransactionContext;
import com.e_bank.enums.TransactionMethod;
import com.e_bank.enums.TransactionType;
import com.e_bank.exception.AccountNotFoundException;
import com.e_bank.exception.BeneficiaryNotFoundException;
import com.e_bank.exception.CardNotFoundException;
import com.e_bank.exception.TransactionNotFoundException;
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
//    public List<Transaction> getAll(){
//        return transactionRepository.findAll();
//    }
//    public Transaction save(Transaction transaction){
//        return transactionRepository.save(transaction);
//    }
    public TransactionDto getById(Long id){
        var transaction = transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);
        return transactionMapper.toDto(transaction);
    }
    public List<TransactionDto> getAllByAccount(Long id){
        return transactionMapper.toDtos(transactionRepository.findAllByAccount_Id(id));
    }
//    public Transaction delete(Long id){
//        transactionRepository.deleteById(id);
//        return transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);
//    }
    public List<TransactionDto> internalTransaction(TransactionDto transactionDto, Long creditId, Long debitId) {
        List<Transaction> transactions = new ArrayList<>();
        var creditAccount = accountRepository.findById(creditId).orElseThrow(AccountNotFoundException::new);
        var debitAccount = accountRepository.findById(debitId).orElseThrow(AccountNotFoundException::new);
        if (creditAccount.getBalance() >= transactionDto.getAmount()) {
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
            creditTransaction.setContext(TransactionContext.Internal);
            debitTransaction.setAccount(debitAccount);
            debitTransaction.setInternalAccount(creditAccount);
            debitTransaction.setDate(Date.valueOf(LocalDate.now()));
            debitTransaction.setTime(Time.valueOf(LocalTime.now()));
            transactions.add(transactionRepository.save(creditTransaction));
            transactions.add(transactionRepository.save(debitTransaction));
            accountRepository.save(creditAccount);
            accountRepository.save(debitAccount);
        }
        return transactionMapper.toDtos(transactions);
    }
    public TransactionDto externalTransaction(TransactionDto transactionDto, Long accountId, Long beneficiaryId){
        var account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        var beneficiary = beneficiaryRepository.findById(beneficiaryId).orElseThrow(BeneficiaryNotFoundException::new);
        var transaction = transactionMapper.toTransaction(transactionDto);
        if (account.getBalance()>=transactionDto.getAmount()){
            account.setBalance(account.getBalance()-transactionDto.getAmount());
            transaction.setContext(TransactionContext.External);
            transaction.setType(TransactionType.Credit);
            transaction.setDate(Date.valueOf(LocalDate.now()));
            transaction.setTime(Time.valueOf(LocalTime.now()));
            transaction.setAccount(account);
            transaction.setBeneficiary(beneficiary);
            accountRepository.save(account);
        }
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }
    public TransactionDto cardTransaction(TransactionDto transactionDto, Long cardId, Long beneficiaryId){
        var card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
        var beneficiary = beneficiaryRepository.findById(beneficiaryId).orElseThrow(BeneficiaryNotFoundException::new);
        var account = accountRepository.findById(card.getAccount().getId()).orElseThrow(AccountNotFoundException::new);
        var transaction = transactionMapper.toTransaction(transactionDto);
        if (account.getBalance()>=transactionDto.getAmount()){
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
        }
        return transactionMapper.toDto(transactionRepository.save(transaction));
    }
}
