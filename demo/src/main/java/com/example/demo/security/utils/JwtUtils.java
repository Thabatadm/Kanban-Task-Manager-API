package com.example.demo.security.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwt.secret}") // Lee desde application.properties
    private String SECRET_KEY;

    @Value("${app.jwt.expiration}") // Lee desde application.properties
    private long EXPIRATION_TIME;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return  Jwts.parser().setSigningKey (SECRET_KEY).parseClaimsJws(token).getBody().getSubject();

    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
