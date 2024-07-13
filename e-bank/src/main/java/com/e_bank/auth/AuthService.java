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

/**
 * Service d'authentification pour gérer la connexion et l'inscription des utilisateurs.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    /**
     * Authentifie l'utilisateur et génère un jeton JWT si les informations sont valides.
     *
     * @param authRequestDTO les informations de connexion de l'utilisateur.
     * @return JwtResponseDTO contenant le jeton JWT si l'authentification est réussie.
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé ou si les informations sont invalides.
     */
    public JwtResponseDTO login(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return JwtResponseDTO.builder().accessToken(jwtService.generateToken(authRequestDTO.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("Invalid user request..!!");
        }
    }

    /**
     * Enregistre un nouvel utilisateur et génère un jeton JWT si l'inscription est réussie.
     *
     * @param userRequest les informations de l'utilisateur à enregistrer.
     * @return JwtResponseDTO contenant le jeton JWT si l'inscription est réussie.
     * @throws RuntimeException si le nom d'utilisateur ou le mot de passe est absent dans la demande.
     */
    public JwtResponseDTO signUp(UserDto userRequest) {
        if (userRequest.getUsername() == null) {
            throw new RuntimeException("Parameter username is not found in request..!!");
        } else if (userRequest.getPassword() == null) {
            throw new RuntimeException("Parameter password is not found in request..!!");
        }
        var user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return JwtResponseDTO.builder().accessToken(jwtService.generateToken(userRepository.save(user).getUsername())).build();
    }
}
