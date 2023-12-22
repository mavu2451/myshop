package com.example.frontend.restcontroller;

import com.example.frontend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers")
    public String duplicateEmail(@Param("email")String email){
        return customerService.isEmailUnique(email) ? "OK" : "Email đã tồn tại";
    }
}
