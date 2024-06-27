package com.example.DigitalBanking.services;

import com.example.DigitalBanking.DTOs.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomer(Long customerId);
    List<CustomerDTO> listCustomers();
    CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    Page<CustomerDTO> searchCustomers(String keyword, Pageable pageable);
}
