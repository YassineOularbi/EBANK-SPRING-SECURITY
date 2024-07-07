package com.e_bank.controller;

import com.e_bank.dto.UserDto;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.exception.UserNotFoundException;
import com.e_bank.model.User;
import com.e_bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("get-all")
    public ResponseEntity<?> getAll(){
        try {
            var users = userService.getAll();
            return ResponseEntity.status(HttpStatus.FOUND).body(users);
        } catch (DatabaseEmptyException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            var user = userService.getById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("add")
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(userDto, id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.delete(id));
    }
}
