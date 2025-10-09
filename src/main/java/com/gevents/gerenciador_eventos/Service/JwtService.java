package com.gevents.gerenciador_eventos.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String secretString;
    
    private Key key;

    private final long expiration = 1000 * 60 * 60 * 24; // 1 dia

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretString);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication auth) {
        return Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.key)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(this.key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        try {
            final Date expirationDate = Jwts.parserBuilder().setSigningKey(this.key).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expirationDate.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

}
