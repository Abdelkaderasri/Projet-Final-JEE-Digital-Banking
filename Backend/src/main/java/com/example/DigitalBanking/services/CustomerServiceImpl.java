package com.example.DigitalBanking.services;
import com.example.DigitalBanking.DTOs.CustomerDTO;
import com.example.DigitalBanking.entites.Customer;
import com.example.DigitalBanking.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        Customer savedCustomer = customerRepository.save(customer);
        customerDTO.setId(savedCustomer.getId());
        return customerDTO;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll().stream().map(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setEmail(customer.getEmail());
            return customerDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        Customer updatedCustomer = customerRepository.save(customer);
        customerDTO.setId(updatedCustomer.getId());
        return customerDTO;
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public Page<CustomerDTO> searchCustomers(String keyword, Pageable pageable) {
        Page<Customer> customers = customerRepository.findByNameContainingOrEmailContaining(keyword, keyword, pageable);
        return customers.map(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setEmail(customer.getEmail());
            return customerDTO;
        });
    }
}
