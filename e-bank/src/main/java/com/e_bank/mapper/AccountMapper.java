package com.e_bank.mapper;

import com.e_bank.dto.*;
import com.e_bank.model.Account;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "user.id", source = "user_id")
    Account toAccount(AccountDto accountDto);

    AccountClosingDto toDto(Account account);
    @Mapping(target = "user_id", source = "user.id")
    AccountDto toAccountDto(Account account);
    @Mapping(target = "user.id", source = "user_id")
    List<Account> toAccounts(List<AccountDto> accountDtos);
    @Mapping(target = "user_id", source = "user.id")
    List<AccountDto> toAccountDtos(List<Account> accounts);
    @Mapping(target = "user.id", source = "user_id")
    Account updateAccountFromDto(AccountDto accountDto, @MappingTarget Account account);
    Account updateAccountFromClosingDto(AccountClosingDto accountDto, @MappingTarget Account account);
}
