package com.bloggingapp.bloggingapp.security.jwt;

import com.bloggingapp.bloggingapp.users.dtos.UserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication implements Authentication {

    private String jwtString;
    private UserResponseDto userResponseDto; //here working as the authentication principal

    public JwtAuthentication(String jwtString) {
        this.jwtString = jwtString;
    }

    public void setUserResponseDto(UserResponseDto userResponseDto) {
        this.userResponseDto = userResponseDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        used when we have different resources accessible to different roles.
        return null;
    }

    @Override
    public String getCredentials() {
//        this is where we return the string/token used for authentication
        return jwtString;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
//        this is where we return the user/client who is getting authenticated
        return userResponseDto;
    }

    @Override
    public boolean isAuthenticated() {
        return userResponseDto!=null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
