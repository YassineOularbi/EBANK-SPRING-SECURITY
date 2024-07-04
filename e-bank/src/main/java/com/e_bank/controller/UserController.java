package com.e_bank.controller;

import com.e_bank.dto.UserDto;
import com.e_bank.model.User;
import com.e_bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getById(id));
    }
    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.update(userDto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.delete(id));
    }
}
