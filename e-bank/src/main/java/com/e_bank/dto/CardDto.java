package com.e_bank.dto;

import com.e_bank.enums.*;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {
    private CardType type;
    private NetworkType network;
    private CardTier tier;
    private Long account_id;
}
