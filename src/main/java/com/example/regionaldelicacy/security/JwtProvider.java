package com.example.regionaldelicacy.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.regionaldelicacy.models.User;

@Service
public class JwtProvider {

    @Value("${security.jwt.token.secret-key}")
    private String JWT_SECRET;

    @Value("${security.jwt.token.expire-length}")
    private long JWT_EXPIRE_LENGTH;

    public String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withClaim("userId", user.getUserId())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRE_LENGTH))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating token", exception);
        }
    }

    public Long validatedToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaim("userId")
                .asLong();
    }
}
