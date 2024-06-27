package com.example.DigitalBanking.controllers;

import com.example.DigitalBanking.DTOs.BankAccountDTO;
import com.example.DigitalBanking.entites.AccountOperation;
import com.example.DigitalBanking.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:4200" )
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping
    public BankAccountDTO saveBankAccount(@RequestBody BankAccountDTO bankAccountDTO) {
        return bankAccountService.saveBankAccount(bankAccountDTO);
    }

    @GetMapping("/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) {
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping
    public List<BankAccountDTO> listBankAccounts() {
        return bankAccountService.listBankAccounts();
    }

    @PutMapping("/{id}")
    public BankAccountDTO updateBankAccount(@PathVariable String id, @RequestBody BankAccountDTO bankAccountDTO) {
        return bankAccountService.updateBankAccount(id, bankAccountDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBankAccount(@PathVariable String id) {
        bankAccountService.deleteBankAccount(id);
    }

    @GetMapping("/search")
    public Page<BankAccountDTO> searchBankAccounts(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        return bankAccountService.searchBankAccounts(keyword, PageRequest.of(page, size));
    }

    @GetMapping("/{accountId}/operations")
    public List<AccountOperation> getAccountOperations(@PathVariable String accountId) {
        return bankAccountService.getAccountOperations(accountId);
    }

    @PostMapping("/{accountId}/operations")
    public AccountOperation addAccountOperation(@PathVariable String accountId, @RequestBody AccountOperation operation) {
        return bankAccountService.addAccountOperation(accountId, operation);
    }
}
