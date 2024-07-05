package com.e_bank.dto;

import com.e_bank.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private AccountType type;
    private Double balance;
    private Long user_id;
}
