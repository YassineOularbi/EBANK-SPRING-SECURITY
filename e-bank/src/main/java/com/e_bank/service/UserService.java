package com.e_bank.service;

import com.e_bank.dto.UserDto;
import com.e_bank.exception.UserNotFoundException;
import com.e_bank.mapper.UserMapper;
import com.e_bank.model.User;
import com.e_bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    public List<UserDto> getAll(){
        return userMapper.toUserDtos(userRepository.findAll());
    }
    public UserDto save(UserDto userDto) {
        var user = userMapper.toUser(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }
    public UserDto update(UserDto userDto, Long id){
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        var userUpdated = userMapper.updateUserFromDto(userDto, user);
        return userMapper.toUserDto(userRepository.save(userUpdated));
    }
    public UserDto getById(Long id){
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }
    public User delete(Long id){
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return user;
    }
}
