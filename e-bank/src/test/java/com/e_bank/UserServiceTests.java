package com.e_bank;

import com.e_bank.dto.UserDto;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.exception.UserNotFoundException;
import com.e_bank.mapper.UserMapper;
import com.e_bank.model.User;
import com.e_bank.repository.UserRepository;
import com.e_bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the UserService.
 */
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    /**
     * Initializes the mock objects.
     */
    public UserServiceTests() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the success scenario of retrieving all users.
     */
    @Test
    public void testGetAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        var result = userService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    /**
     * Tests the scenario where retrieving all users fails due to an empty database.
     */
    @Test
    public void testGetAllUsers_EmptyDatabase() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(DatabaseEmptyException.class, () -> userService.getAll());
    }

    /**
     * Tests the success scenario of saving a user.
     */
    @Test
    public void testSaveUser_Success() {
        UserDto userDto = new UserDto("John Doe", "john.doe@example.com", "0610446080");

        User user = new User();
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        when(userRepository.save(user)).thenReturn(user);

        var savedUser = userService.save(userDto);

        assertNotNull(savedUser);
        assertEquals(userDto.getMail(), savedUser.getMail());
    }

    /**
     * Tests the success scenario of updating a user.
     */
    @Test
    public void testUpdateUser_Success() {
        UserDto userDto = new UserDto("John Doe", "john.doe@example.com", "0610440680");
        Long userId = 1L;

        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User updatedUser = new User();
        when(userMapper.updateUserFromDto(userDto, existingUser)).thenReturn(updatedUser);
        when(userMapper.toUserDto(updatedUser)).thenReturn(userDto);

        var updatedDto = userService.update(userDto, userId);

        assertNotNull(updatedDto);
        assertEquals(userDto.getName(), updatedDto.getName());
    }

    /**
     * Tests the success scenario of retrieving a user by ID.
     */
    @Test
    public void testGetUserById_Success() {
        Long userId = 1L;

        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var fetchedUser = userService.getById(userId);

        assertNotNull(fetchedUser);
    }

    /**
     * Tests the scenario where retrieving a user by ID fails because the user is not found.
     */
    @Test
    public void testGetUserById_NotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(userId));
    }

    /**
     * Tests the success scenario of deleting a user.
     */
    @Test
    public void testDeleteUser_Success() {
        Long userId = 1L;

        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var deletedDto = userService.delete(userId);

        assertNotNull(deletedDto);
    }

}
