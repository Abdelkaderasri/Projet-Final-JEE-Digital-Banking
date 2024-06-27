package com.example.DigitalBanking.entites;

import jakarta.persistence.Entity;

@Entity
public class CurrentAccount extends BankAccount {
    private double overdraft;

}
