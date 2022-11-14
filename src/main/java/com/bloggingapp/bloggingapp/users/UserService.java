package com.bloggingapp.bloggingapp.users;

import com.bloggingapp.bloggingapp.users.dtos.CreateUserRequestDto;
import com.bloggingapp.bloggingapp.users.dtos.LoginUserDto;
import com.bloggingapp.bloggingapp.users.dtos.UserResponseDto;
import com.bloggingapp.bloggingapp.users.exceptions.InvalidPasswordException;
import com.bloggingapp.bloggingapp.users.exceptions.UserNotFoundException;
import com.bloggingapp.bloggingapp.users.mappers.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = UserMapper.INSTANCE;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        UserEntity userEntity = userMapper.mapCreateUserRequestDtoToUserEntity(createUserRequestDto);
        userEntity.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.mapUserEntityToUserResponseDto(savedUserEntity);
    }

    public UserResponseDto verifyUser(LoginUserDto loginUserDto) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(loginUserDto.getUsername());
        if(userEntity == null) {
            throw new UserNotFoundException(loginUserDto.getUsername());
        }
        if(!passwordEncoder.matches(loginUserDto.getPassword(), userEntity.getPassword())) {
            throw new InvalidPasswordException();
        }
        return userMapper.mapUserEntityToUserResponseDto(userEntity);
    }
}
