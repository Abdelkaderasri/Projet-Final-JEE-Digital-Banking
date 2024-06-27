package com.example.DigitalBanking.services;

import com.example.DigitalBanking.DTOs.BankAccountDTO;
import com.example.DigitalBanking.entites.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankAccountService {
    BankAccountDTO saveBankAccount(BankAccountDTO bankAccountDTO);
    BankAccountDTO getBankAccount(String accountId);
    List<BankAccountDTO> listBankAccounts();

    BankAccountDTO updateBankAccount(String accountId, BankAccountDTO bankAccountDTO);

    void deleteBankAccount(String accountId);

    Page<BankAccountDTO> searchBankAccounts(String keyword, Pageable pageable);
    List<AccountOperation> getAccountOperations(String accountId);
    AccountOperation addAccountOperation(String accountId, AccountOperation operation);

}
