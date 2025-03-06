package com.example.noleggioautobe.auth;

import com.example.noleggioautobe.entities.Utente;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "secretsecretsecretsecretsecretsecretsecretsecret";

    private final SecretKey secret_key;
    private final JwtParser jwtParser;

    public JwtUtil(){
        secret_key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secret_key).build();
    }

    public String createToken(Utente user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("isAdmin",user.getIsAdmin());
        Date tokenCreateTime = new Date();
        long accessTokenValidity = 60 * 60 * 1000;
        Date tokenValidity = new Date(tokenCreateTime.getTime() + accessTokenValidity);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String getUsername(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parse(token);
        return true;

    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}