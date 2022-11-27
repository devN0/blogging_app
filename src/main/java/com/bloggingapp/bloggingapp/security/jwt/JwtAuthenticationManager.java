package com.bloggingapp.bloggingapp.security.jwt;

import com.bloggingapp.bloggingapp.users.UserService;
import com.bloggingapp.bloggingapp.users.dtos.UserResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationManager implements AuthenticationManager {
    private JwtService jwtService;
    private UserService userService;

    public JwtAuthenticationManager(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

//    we'll take the Authentication obj from the converter(with token set), get the credentials from it and validate
//    token signature, and expiry date
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        we'll first make sure that our JwtManager is being served an instance of JwtAuthentication
        if(authentication instanceof JwtAuthentication) {
            JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
            String jwtString = jwtAuthentication.getCredentials();
//            TODO: try-catch around getUsernameFromJwt for handling:
//             - crypto failure on jwt varification
//             - jwt expiry
            String username = jwtService.getUsernameFromJwt(jwtString);
            UserResponseDto userResponseDto = userService.getUserFromUsername(username);
            jwtAuthentication.setUserResponseDto(userResponseDto);
            return jwtAuthentication;
        }
        return null;
    }
}
