package com.e_bank.mapper;

import com.e_bank.dto.TransactionDto;
import com.e_bank.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransaction(TransactionDto transactionDto);
    TransactionDto toDto(Transaction transaction);
    List<TransactionDto> toDtos(List<Transaction> transactions);
}
