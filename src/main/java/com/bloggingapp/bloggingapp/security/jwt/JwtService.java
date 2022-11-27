package com.bloggingapp.bloggingapp.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final String secretKey = "j04jnt043jng043486ht3j3j93ljknt3";
    private Algorithm algorithm = Algorithm.HMAC256(secretKey);

    public String createJwt(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public String getUsernameFromJwt(String jwt) {
        return JWT.require(algorithm)
                .build()
                .verify(jwt)
                .getSubject();
    }

}
