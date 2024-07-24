package com.e_bank.model;

import com.e_bank.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Integer cvv;
    @DateTimeFormat(pattern = "MM-YY")
    private Date expirationDate;
    private Boolean isActivated;
    private CardType type;
    private NetworkType network;
    private CardTier tier;
    private Boolean isBlocked;
    private String BlockingReason;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Transaction> transactions;
}
