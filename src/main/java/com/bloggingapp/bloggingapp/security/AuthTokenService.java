package com.bloggingapp.bloggingapp.security;

import com.bloggingapp.bloggingapp.users.UserEntity;
import com.bloggingapp.bloggingapp.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthTokenService {
    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;

    public AuthTokenService(AuthTokenRepository authTokenRepository, UserRepository userRepository) {
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
    }

    public String createToken(UserEntity userEntity) {
        AuthTokenEntity authTokenEntity = new AuthTokenEntity();
        authTokenEntity.setUser(userEntity);
        AuthTokenEntity savedAuthTokenEntity = authTokenRepository.save(authTokenEntity); // automatically generating unique uuid token as id
        String authToken = savedAuthTokenEntity.getToken().toString();
        return authToken;
    }

    public UserEntity getUserFromToken(String token) {
        AuthTokenEntity authTokenEntity = authTokenRepository.findById(UUID.fromString(token)).orElseThrow();
        return authTokenEntity.getUser();
    }


}
