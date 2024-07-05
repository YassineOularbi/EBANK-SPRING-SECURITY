package com.e_bank.dto;

import com.e_bank.enums.CardTier;
import com.e_bank.enums.CardType;
import com.e_bank.enums.NetworkType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private CardType type;
    private NetworkType network;
    private CardTier tier;
    private Long account_id;
}
