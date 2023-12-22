package com.example.frontend.service;

import com.example.common.entity.Customer;
import com.example.common.entity.User;
import com.example.frontend.config.CustomerDetailsConfig;
import com.example.frontend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsConfigService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer!=null){
            return new CustomerDetailsConfig(customer);
        }
        throw new UsernameNotFoundException("Thông tin đăng nhập không hợp lệ");
    }
}
