package com.e_bank.model;

import com.e_bank.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private AccountType type;
    private Double balance;
    private Date date;
    private String IBAN;
    private Boolean isClosed;
    private String closingReason;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Card> cards;
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Beneficiary> beneficiaries;
}
