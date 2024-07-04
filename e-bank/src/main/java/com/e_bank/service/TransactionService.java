package com.e_bank.service;

import com.e_bank.exception.TransactionNotFoundException;
import com.e_bank.model.Transaction;
import com.e_bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    public List<Transaction> getAll(){
        return transactionRepository.findAll();
    }
    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }
    public Transaction getById(Long id){
        return transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);
    }
    public Transaction delete(Long id){
        transactionRepository.deleteById(id);
        return transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);
    }
}
