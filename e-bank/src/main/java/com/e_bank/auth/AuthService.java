package com.e_bank.auth;

import com.e_bank.dto.*;
import com.e_bank.mapper.UserMapper;
import com.e_bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    public JwtResponseDTO login(AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDTO.builder().accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    public JwtResponseDTO signUp(UserDto userRequest) {
        if(userRequest.getUsername() == null){
            throw new RuntimeException("Parameter username is not found in request..!!");
        } else if(userRequest.getPassword() == null){
            throw new RuntimeException("Parameter password is not found in request..!!");
        }
        var user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return JwtResponseDTO.builder().accessToken(jwtService.GenerateToken(userRepository.save(user).getUsername())).build();
    }
}
