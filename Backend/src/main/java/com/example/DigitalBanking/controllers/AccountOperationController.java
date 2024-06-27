package com.example.DigitalBanking.controllers;

import com.example.DigitalBanking.entites.OperationType;
import com.example.DigitalBanking.repositories.AccountOperationRepository;
import com.example.DigitalBanking.services.AccountOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/operations")
public class AccountOperationController {

    @Autowired
    private AccountOperationRepository accountOperationRepository;

    @Autowired
    private AccountOperationService accountOperationService;

    @GetMapping("/customer-counts")
    public ResponseEntity<Map<String, Long>> getOperationCountsByCustomer() {
        Map<String, Long> counts = accountOperationService.getOperationCountsByCustomer();
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/counts")
    public Map<String, Long> getOperationCounts() {
        long creditCount = accountOperationRepository.countByType(OperationType.CREDIT);
        long debitCount = accountOperationRepository.countByType(OperationType.DEBIT);
        return Map.of("CREDIT", creditCount, "DEBIT", debitCount);
    }
}