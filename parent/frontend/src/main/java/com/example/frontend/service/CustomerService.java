package com.example.frontend.service;


import com.example.common.entity.Customer;
import com.example.frontend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    public boolean isEmailUnique(String email){
        Customer customer = customerRepository.findByEmail(email);
        return customer == null;
    }
}
