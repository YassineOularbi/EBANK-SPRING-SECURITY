package com.e_bank.service;

import com.e_bank.dto.UserDto;
import com.e_bank.exception.*;
import com.e_bank.mapper.UserMapper;
import com.e_bank.model.User;
import com.e_bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for managing users in the E-Bank application.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    /**
     * Retrieves all users from the database.
     *
     * @return List of users
     * @throws DatabaseEmptyException if no users are found
     */
    public List<User> getAll() {
        var users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new DatabaseEmptyException();
        }
        return users;
    }

    /**
     * Saves a new user.
     *
     * @param userDto User data to be saved
     * @return Saved user DTO
     */
    public UserDto save(UserDto userDto) {
        var user = userMapper.toUser(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }

    /**
     * Updates an existing user.
     *
     * @param userDto User data to update
     * @param id      ID of the user to update
     * @return Updated user DTO
     * @throws UserNotFoundException if the user with given ID is not found
     */
    public UserDto update(UserDto userDto, Long id) throws UserNotFoundException {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        var userUpdated = userMapper.updateUserFromDto(userDto, user);
        return userMapper.toUserDto(userRepository.save(userUpdated));
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id ID of the user to retrieve
     * @return User entity
     * @throws UserNotFoundException if the user with given ID is not found
     */
    public User getById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id ID of the user to delete
     * @return Deleted user DTO
     * @throws UserNotFoundException if the user with given ID is not found
     */
    public UserDto delete(Long id) throws UserNotFoundException {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return userMapper.toUserDto(user);
    }
}
