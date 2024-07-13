package com.e_bank.dto;

import com.e_bank.enums.AccountType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private AccountType type;
    private Double balance;
    private Long user_id;
}
