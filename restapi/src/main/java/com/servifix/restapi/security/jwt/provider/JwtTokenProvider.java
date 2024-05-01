package com.servifix.restapi.security.jwt.provider;

import com.servifix.restapi.security.Utils.Utilities;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    //obtiene una propiedad del application.properties
    @Value("${app.jwt-secret}")
    private String secret;

    @Value("${app.jwt-expiration-min}")
    private long expiration;

    public String generateToken(Authentication authentication) {
        var authenticatedUser = (User) authentication.getPrincipal();

        //in minutes
        var expiryDate = new Date(new Date().getTime() + expiration * 60_000);

        //construye el token JWT
        return Jwts.builder()
                .subject(authenticatedUser.getUsername()) //se usa el username como subject
                .issuedAt(new Date()) //fecha de emisión
                .expiration(expiryDate) //fecha de expiración
                .signWith(Utilities.getKey(secret)) //firma el token
                .claim("roles", Utilities.getRoles(authenticatedUser)) //agrega los roles
                .compact(); //construye el token
    }

    /**
     * Obtiene el ID del usuario a partir del token
     * @param token Token a procesar
     * @return ID del usuario
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Utilities.getKey(secret)) //verifica la firma
                .build() //construye el parser
                .parseSignedClaims(token) //parsea el token
                .getPayload(); //obtiene los claims (payload)

        return claims.getSubject();
    }

    /**
     * Valida el token
     * @param token Token a validar
     * @return True = token válido
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Utilities.getKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (MalformedJwtException ex) {
            log.warn("Token inválido");
        } catch (ExpiredJwtException ex) {
            log.warn("Token expirado");
        } catch (UnsupportedJwtException ex) {
            log.warn("Token no soportado");
        } catch (IllegalArgumentException ex) {
            log.warn("Claims vacíos");
        }

        return false;
    }
}
