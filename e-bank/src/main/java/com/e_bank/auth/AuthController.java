package com.e_bank.auth;

import com.e_bank.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;
    @PostMapping("/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response){
        try {
            var jwtResponseDTO = userService.login(authRequestDTO);
            return ResponseEntity.ok(jwtResponseDTO);
        } catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userRequest) {
        try {
            var jwtResponseDTO = userService.signUp(userRequest);
            return ResponseEntity.ok(jwtResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
