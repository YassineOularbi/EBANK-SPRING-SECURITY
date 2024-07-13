package com.e_bank.mapper;

import com.e_bank.dto.TransactionDto;
import com.e_bank.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T15:54:06+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toTransaction(TransactionDto transactionDto) {
        if ( transactionDto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setAmount( transactionDto.getAmount() );
        transaction.setDescription( transactionDto.getDescription() );
        transaction.setMethod( transactionDto.getMethod() );

        return transaction;
    }

    @Override
    public TransactionDto toDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setAmount( transaction.getAmount() );
        transactionDto.setDescription( transaction.getDescription() );
        transactionDto.setMethod( transaction.getMethod() );

        return transactionDto;
    }

    @Override
    public List<TransactionDto> toDtos(List<Transaction> transactions) {
        if ( transactions == null ) {
            return null;
        }

        List<TransactionDto> list = new ArrayList<TransactionDto>( transactions.size() );
        for ( Transaction transaction : transactions ) {
            list.add( toDto( transaction ) );
        }

        return list;
    }
}
