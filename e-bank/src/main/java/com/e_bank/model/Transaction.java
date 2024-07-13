package com.e_bank.model;

import com.e_bank.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Date date;
    private Time time;
    private String description;
    private TransactionType type;
    private TransactionContext context;
    private TransactionMethod method;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "internal_account_id")
    private Account internalAccount;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;
    @JsonIgnore
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "card_id")
    private Card card;
}
