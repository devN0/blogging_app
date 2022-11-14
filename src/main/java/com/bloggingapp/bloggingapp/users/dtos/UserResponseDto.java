package com.bloggingapp.bloggingapp.users.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    @JsonProperty("user name")
    @NonNull
    private String username;
    @NonNull
    private String email;
    private String token;
}
