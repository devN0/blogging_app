package com.bloggingapp.bloggingapp.users.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class CreateUserRequestDto {
    @NonNull
    @JsonProperty("user name")
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
