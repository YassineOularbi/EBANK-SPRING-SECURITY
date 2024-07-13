package com.e_bank.mapper;

import com.e_bank.dto.AccountClosingDto;
import com.e_bank.dto.AccountDto;
import com.e_bank.model.Account;
import com.e_bank.model.User;
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
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toAccount(AccountDto accountDto) {
        if ( accountDto == null ) {
            return null;
        }

        Account account = new Account();

        account.setUser( accountDtoToUser( accountDto ) );
        account.setType( accountDto.getType() );
        account.setBalance( accountDto.getBalance() );

        return account;
    }

    @Override
    public AccountClosingDto toDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountClosingDto accountClosingDto = new AccountClosingDto();

        accountClosingDto.setClosingReason( account.getClosingReason() );

        return accountClosingDto;
    }

    @Override
    public AccountDto toAccountDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setUser_id( accountUserId( account ) );
        accountDto.setType( account.getType() );
        accountDto.setBalance( account.getBalance() );

        return accountDto;
    }

    @Override
    public List<Account> toAccounts(List<AccountDto> accountDtos) {
        if ( accountDtos == null ) {
            return null;
        }

        List<Account> list = new ArrayList<Account>( accountDtos.size() );
        for ( AccountDto accountDto : accountDtos ) {
            list.add( toAccount( accountDto ) );
        }

        return list;
    }

    @Override
    public List<AccountDto> toAccountDtos(List<Account> accounts) {
        if ( accounts == null ) {
            return null;
        }

        List<AccountDto> list = new ArrayList<AccountDto>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( toAccountDto( account ) );
        }

        return list;
    }

    @Override
    public Account updateAccountFromDto(AccountDto accountDto, Account account) {
        if ( accountDto == null ) {
            return account;
        }

        if ( account.getUser() == null ) {
            account.setUser( new User() );
        }
        accountDtoToUser1( accountDto, account.getUser() );
        account.setType( accountDto.getType() );
        account.setBalance( accountDto.getBalance() );

        return account;
    }

    @Override
    public Account updateAccountFromClosingDto(AccountClosingDto accountDto, Account account) {
        if ( accountDto == null ) {
            return account;
        }

        account.setClosingReason( accountDto.getClosingReason() );

        return account;
    }

    protected User accountDtoToUser(AccountDto accountDto) {
        if ( accountDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( accountDto.getUser_id() );

        return user;
    }

    private Long accountUserId(Account account) {
        if ( account == null ) {
            return null;
        }
        User user = account.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected void accountDtoToUser1(AccountDto accountDto, User mappingTarget) {
        if ( accountDto == null ) {
            return;
        }

        mappingTarget.setId( accountDto.getUser_id() );
    }
}
