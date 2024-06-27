package com.example.DigitalBanking.entites;

import jakarta.persistence.*;


import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type")
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;

    @ManyToOne
    private Customer customer;
    @ManyToOne
    private AppUser createdBy;
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<AccountOperation> operations;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<AccountOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<AccountOperation> operations) {
        this.operations = operations;
    }

    public void setType(String current) {
    }

    public void setCreatedBy(AppUser user) {
        this.createdBy = user;
    }
}
