package com.example.backend.service;


import com.example.backend.exception.UserNotFoundException;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;

import com.example.common.entity.Role;
import com.example.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {
    public static final int USER_PAGE = 3;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll(){
        return (List<User>) userRepo.findAll();
    }

    public List<Role> listRoles(){
        return (List<Role>) roleRepo.findAll();
    }

    public Page<User> listByPage(int pageNum){
        Pageable p = PageRequest.of(pageNum - 1,USER_PAGE);
        return userRepo.findAll(p);
    }
    public User save(User user) {
        boolean update = (user.getId() !=null);
        if(update){
            User exist = userRepo.findById(user.getId()).get();
            if(user.getPassword().isEmpty()) {
                user.setPassword(exist.getPassword());
            }
            else{
                encodePassword(user);
            }
        }else {
            encodePassword(user);
        }
        return userRepo.save(user);
    }

    public void encodePassword(User user){
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
    }

    public boolean isEmailUnique(Integer id, String email){
        User userByEmail = userRepo.getUserByEmail(email);
        if(userByEmail == null) return true;
        boolean isNew = (id == null);
        if(isNew){
            if(userByEmail!=null) return false;
        }else{
            if(userByEmail.getId()!=id){
                return false;
            }
        }
        return true;
    }
    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        }catch (NoSuchElementException e){
            throw new UserNotFoundException("Không tìm thấy tài khoản " + id);
        }
    }
    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);
        if(countById == null || countById == 0){
            throw new UserNotFoundException("Không tìm thấy tài khoản " + id);
        }
        userRepo.deleteById(id);
    }
    public void updateUserEnabled(Integer id, boolean enabled){
        userRepo.updateEnabledStatus(id,enabled);
    }
}
