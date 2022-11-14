package com.bloggingapp.bloggingapp.security;

import com.bloggingapp.bloggingapp.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "auth_tokens")
public class AuthTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID token;
    @ManyToOne
    private UserEntity user;
}
