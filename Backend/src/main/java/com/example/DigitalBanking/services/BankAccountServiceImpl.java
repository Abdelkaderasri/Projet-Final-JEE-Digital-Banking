package com.example.DigitalBanking.services;

import com.example.DigitalBanking.DTOs.BankAccountDTO;
import com.example.DigitalBanking.DTOs.CustomerDTO;
import com.example.DigitalBanking.entites.AccountOperation;
import com.example.DigitalBanking.entites.BankAccount;
import com.example.DigitalBanking.entites.CurrentAccount;
import com.example.DigitalBanking.entites.Customer;
import com.example.DigitalBanking.repositories.AccountOperationRepository;
import com.example.DigitalBanking.repositories.BankAccountRepository;
import com.example.DigitalBanking.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {


    private final BankAccountRepository bankAccountRepository;

    @Autowired
    private AccountOperationRepository accountOperationRepository;

    private final CustomerRepository customerRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public BankAccountDTO saveBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new CurrentAccount(); // or SavingAccount based on your logic
        bankAccount.setId(bankAccountDTO.getId());
        bankAccount.setBalance(bankAccountDTO.getBalance());
        bankAccount.setCreatedAt(bankAccountDTO.getCreatedAt());

        Customer customer = customerRepository.findById(bankAccountDTO.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        bankAccount.setCustomer(customer);

        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        bankAccountDTO.setId(savedBankAccount.getId());
        return bankAccountDTO;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setBalance(bankAccount.getBalance());
        bankAccountDTO.setCreatedAt(bankAccount.getCreatedAt());
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(bankAccount.getCustomer().getId());
        customerDTO.setName(bankAccount.getCustomer().getName());
        customerDTO.setEmail(bankAccount.getCustomer().getEmail());
        bankAccountDTO.setCustomer(customerDTO);
        return bankAccountDTO;
    }

    @Override
    public List<BankAccountDTO> listBankAccounts() {
        return bankAccountRepository.findAll().stream().map(bankAccount -> {
            BankAccountDTO bankAccountDTO = new BankAccountDTO();
            bankAccountDTO.setId(bankAccount.getId());
            bankAccountDTO.setBalance(bankAccount.getBalance());
            bankAccountDTO.setCreatedAt(bankAccount.getCreatedAt());
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(bankAccount.getCustomer().getId());
            customerDTO.setName(bankAccount.getCustomer().getName());
            customerDTO.setEmail(bankAccount.getCustomer().getEmail());
            bankAccountDTO.setCustomer(customerDTO);
            return bankAccountDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO updateBankAccount(String accountId, BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        bankAccount.setBalance(bankAccountDTO.getBalance());
        bankAccount.setCreatedAt(bankAccountDTO.getCreatedAt());

        Customer customer = customerRepository.findById(bankAccountDTO.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        bankAccount.setCustomer(customer);

        BankAccount updatedBankAccount = bankAccountRepository.save(bankAccount);
        bankAccountDTO.setId(updatedBankAccount.getId());
        return bankAccountDTO;
    }

    @Override
    public void deleteBankAccount(String accountId) {
        bankAccountRepository.deleteById(accountId);
    }

    @Override
    public Page<BankAccountDTO> searchBankAccounts(String keyword, Pageable pageable) {
        Page<BankAccount> bankAccounts = bankAccountRepository.findByCustomerNameContainingOrIdContaining(keyword, keyword, pageable);
        return bankAccounts.map(bankAccount -> {
            BankAccountDTO bankAccountDTO = new BankAccountDTO();
            bankAccountDTO.setId(bankAccount.getId());
            bankAccountDTO.setBalance(bankAccount.getBalance());
            bankAccountDTO.setCreatedAt(bankAccount.getCreatedAt());
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(bankAccount.getCustomer().getId());
            customerDTO.setName(bankAccount.getCustomer().getName());
            customerDTO.setEmail(bankAccount.getCustomer().getEmail());
            bankAccountDTO.setCustomer(customerDTO);
            return bankAccountDTO;
        });
    }

    @Override
    public List<AccountOperation> getAccountOperations(String accountId) {
        return accountOperationRepository.findByBankAccountId(accountId);
    }

    @Override
    public AccountOperation addAccountOperation(String accountId, AccountOperation operation) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountId);
        if (bankAccount.isPresent()) {
            operation.setBankAccount(bankAccount.get());
            return accountOperationRepository.save(operation);
        } else {
            throw new RuntimeException("Bank account not found");
        }
    }
}
