package com.e_bank.dto;


import com.e_bank.enums.TransactionMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Double amount;
    private String description;
    private TransactionMethod method;
}
