package com.e_bank.dto;


import com.e_bank.enums.TransactionMethod;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private Double amount;
    private String description;
    private TransactionMethod method;
}
