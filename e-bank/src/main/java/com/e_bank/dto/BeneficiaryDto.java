package com.e_bank.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryDto {
    private String name;
    private String IBAN;
    private String bank;
    private Long account_id;
}
