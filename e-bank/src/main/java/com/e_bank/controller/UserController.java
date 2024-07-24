package com.e_bank.controller;

import com.e_bank.dto.UserDto;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.exception.UserNotFoundException;
import com.e_bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity with the list of users or status 404 if no users found.
     */

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        try {
            var users = userService.getAll();
            return ResponseEntity.ok(users);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id ID of the user to retrieve.
     * @return ResponseEntity with the user details or status 404 if user not found.
     */
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            var user = userService.getById(Long.valueOf(id));
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Adds a new user.
     *
     * @param userDto User data to be saved.
     * @return ResponseEntity with the saved user details and status 201 if successful.
     */
    @PostMapping("/add")
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        var savedUser = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Updates an existing user.
     *
     * @param id      ID of the user to update.
     * @param userDto Updated user data.
     * @return ResponseEntity with the updated user details or status 404 if user not found.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        try {
            var updatedUser = userService.update(userDto, Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param id ID of the user to delete.
     * @return ResponseEntity with the deleted user details or status 404 if user not found.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            var deletedUser = userService.delete(Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedUser);
        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
