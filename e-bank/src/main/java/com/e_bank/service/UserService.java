package com.e_bank.service;

import com.e_bank.dto.UserDto;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.exception.UserNotFoundException;
import com.e_bank.mapper.UserMapper;
import com.e_bank.model.User;
import com.e_bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    public List<User> getAll(){
        var users = userRepository.findAll();
        if (users.isEmpty()){
            throw new DatabaseEmptyException();
        }
        return users;
    }
    public UserDto save(UserDto userDto) {
        var user = userMapper.toUser(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }
    public UserDto update(UserDto userDto, Long id) throws UserNotFoundException{
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        var userUpdated = userMapper.updateUserFromDto(userDto, user);
        return userMapper.toUserDto(userRepository.save(userUpdated));
    }
    public User getById(Long id) throws UserNotFoundException{
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
    public UserDto delete(Long id) throws UserNotFoundException{
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return userMapper.toUserDto(user);
    }
}
