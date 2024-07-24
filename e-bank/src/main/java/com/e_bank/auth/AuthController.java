package com.e_bank.auth;

import com.e_bank.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour la gestion de l'authentification et de l'inscription des utilisateurs.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService userService;

    /**
     * Authentifie l'utilisateur et retourne un jeton JWT si l'authentification réussit.
     *
     * @param authRequestDTO les informations de connexion de l'utilisateur.
     * @param response l'objet HttpServletResponse.
     * @return ResponseEntity contenant le jeton JWT si l'authentification est réussie, ou un message d'erreur en cas d'échec.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response) {
        try {
            var jwtResponseDTO = userService.login(authRequestDTO);
            return ResponseEntity.ok(jwtResponseDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Enregistre un nouvel utilisateur et retourne un jeton JWT si l'inscription est réussie.
     *
     * @param userRequest les informations de l'utilisateur à enregistrer.
     * @return ResponseEntity contenant le jeton JWT si l'inscription est réussie, ou un message d'erreur en cas d'échec.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userRequest) {
        try {
            var jwtResponseDTO = userService.signUp(userRequest);
            return ResponseEntity.ok(jwtResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
