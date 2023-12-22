package com.example.frontend.controller;


import com.example.frontend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("pageTitle","Đăng ký tài khoản");
        return "register";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
