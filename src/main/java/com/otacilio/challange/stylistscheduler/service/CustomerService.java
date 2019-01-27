package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.model.Customer;
import com.otacilio.challange.stylistscheduler.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer find(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer create(Customer customer) {
        Customer new_customer = repository.save(customer);
        return new_customer;
    }
}
