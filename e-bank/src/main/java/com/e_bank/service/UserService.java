package com.e_bank.service;

import com.e_bank.model.User;
import com.e_bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User getById(Long id){
        return userRepository.findById(id).orElseThrow();
    }
    public User delete(Long id){
        userRepository.deleteById(id);
        return userRepository.findById(id).orElseThrow();
    }
}
