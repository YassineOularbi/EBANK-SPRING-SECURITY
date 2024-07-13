package com.e_bank.mapper;

import com.e_bank.dto.TransactionDto;
import com.e_bank.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-13T16:48:02+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toTransaction(TransactionDto transactionDto) {
        if ( transactionDto == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        transaction.amount( transactionDto.getAmount() );
        transaction.description( transactionDto.getDescription() );
        transaction.method( transactionDto.getMethod() );

        return transaction.build();
    }

    @Override
    public TransactionDto toDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionDto.TransactionDtoBuilder transactionDto = TransactionDto.builder();

        transactionDto.amount( transaction.getAmount() );
        transactionDto.description( transaction.getDescription() );
        transactionDto.method( transaction.getMethod() );

        return transactionDto.build();
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
