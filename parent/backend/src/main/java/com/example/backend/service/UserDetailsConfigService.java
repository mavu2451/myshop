package com.example.backend.service;

import com.example.backend.config.UserDetailsConfig;
import com.example.backend.repository.UserRepository;
import com.example.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsConfigService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if(user!=null){
            return new UserDetailsConfig(user);
        }
        throw new UsernameNotFoundException("Thông tin đăng nhập không hợp lệ");
    }
}
