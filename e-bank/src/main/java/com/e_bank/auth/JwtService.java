package com.e_bank.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.time.Instant.now;

/**
 * Service pour la gestion des tokens JWT.
 */
@Component
public class JwtService {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    /**
     * Extrait le nom d'utilisateur du token JWT.
     *
     * @param token le token JWT.
     * @return le nom d'utilisateur.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait la date d'expiration du token JWT.
     *
     * @param token le token JWT.
     * @return la date d'expiration.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait une revendication spécifique du token JWT.
     *
     * @param token le token JWT.
     * @param claimsResolver la fonction pour extraire la revendication.
     * @param <T> le type de la revendication.
     * @return la valeur de la revendication.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait toutes les revendications du token JWT.
     *
     * @param token le token JWT.
     * @return les revendications.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Vérifie si le token JWT est expiré.
     *
     * @param token le token JWT.
     * @return vrai si le token est expiré, sinon faux.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Valide le token JWT en vérifiant le nom d'utilisateur et l'expiration.
     *
     * @param token le token JWT.
     * @param userDetails les détails de l'utilisateur.
     * @return vrai si le token est valide, sinon faux.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Génère un token JWT pour un nom d'utilisateur.
     *
     * @param username le nom d'utilisateur.
     * @return le token JWT généré.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Crée un token JWT avec des revendications et un nom d'utilisateur.
     *
     * @param claims les revendications.
     * @param username le nom d'utilisateur.
     * @return le token JWT créé.
     */
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(now()))
                .setExpiration(Date.from(now().plus(24*60, ChronoUnit.MINUTES)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Obtient la clé de signature utilisée pour signer le token JWT.
     *
     * @return la clé de signature.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
