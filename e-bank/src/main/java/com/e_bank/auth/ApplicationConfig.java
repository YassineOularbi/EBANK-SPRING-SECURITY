package com.e_bank.auth;

import com.e_bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration de l'application pour la gestion de la sécurité et l'authentification.
 * Cette classe configure les beans nécessaires pour l'authentification et la gestion des utilisateurs.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * Définit le bean UserDetailsService utilisé pour charger les détails des utilisateurs.
     *
     * @return UserDetailsService une instance de UserDetailsService qui charge les utilisateurs à partir du repository.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    /**
     * Définit le bean PasswordEncoder utilisé pour encoder les mots de passe.
     *
     * @return PasswordEncoder une instance de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Définit le bean AuthenticationProvider utilisé pour l'authentification.
     *
     * @return AuthenticationProvider une instance de DaoAuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Définit le bean AuthenticationManager utilisé pour gérer l'authentification.
     *
     * @param config l'instance de AuthenticationConfiguration.
     * @return AuthenticationManager une instance de AuthenticationManager.
     * @throws Exception si une erreur se produit lors de la configuration du gestionnaire d'authentification.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
