package com.otacilio.challange.stylistscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.otacilio.challange.stylistscheduler.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}