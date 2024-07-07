package com.e_bank.model;

import com.e_bank.enums.TransactionContext;
import com.e_bank.enums.TransactionMethod;
import com.e_bank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internal_account_id")
    private Account internalAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;
}
