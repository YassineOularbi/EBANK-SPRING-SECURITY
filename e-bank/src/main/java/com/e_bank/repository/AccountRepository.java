package com.e_bank.repository;

import com.e_bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getAccountByUser_Id(Long id);
}
