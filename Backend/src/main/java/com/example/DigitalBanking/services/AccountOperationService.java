package com.example.DigitalBanking.services;

import com.example.DigitalBanking.entites.Customer;
import com.example.DigitalBanking.repositories.AccountOperationRepository;
import com.example.DigitalBanking.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountOperationService {

    @Autowired
    private AccountOperationRepository accountOperationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Map<String, Long> getOperationCountsByCustomer() {
        List<Customer> customers = customerRepository.findAll();
        Map<String, Long> operationCounts = new HashMap<>();

        for (Customer customer : customers) {
            Long operationCount = accountOperationRepository.countByCustomerId(customer.getId());
            operationCounts.put(customer.getName(), operationCount);
        }
        return operationCounts;
    }
}
