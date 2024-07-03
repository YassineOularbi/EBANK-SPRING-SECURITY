package com.e_bank.model;

import com.e_bank.enums.CardTier;
import com.e_bank.enums.CardType;
import com.e_bank.enums.NetworkType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Integer cvv;
    private Date expirationDate;
    private Boolean status;
    private CardType type;
    private NetworkType network;
    private CardTier tier;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
