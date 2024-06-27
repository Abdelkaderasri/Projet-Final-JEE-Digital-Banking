package com.example.DigitalBanking.repositories;

import com.example.DigitalBanking.DTOs.BankAccountDTO;
import com.example.DigitalBanking.entites.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Page<BankAccount> findByCustomerNameContainingOrIdContaining(String nameKeyword, String idKeyword, Pageable pageable);

}
