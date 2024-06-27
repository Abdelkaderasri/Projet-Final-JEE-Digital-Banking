package com.example.DigitalBanking.entites;
import jakarta.persistence.Entity;


@Entity
public class SavingAccount extends BankAccount {
    private double interestRate;

    // getters and setters
}
