package com.bloggingapp.bloggingapp.users;

import com.bloggingapp.bloggingapp.users.dtos.CreateUserRequestDto;
import com.bloggingapp.bloggingapp.users.dtos.LoginUserDto;
import com.bloggingapp.bloggingapp.users.dtos.UserResponseDto;
import com.bloggingapp.bloggingapp.users.exceptions.InvalidPasswordException;
import com.bloggingapp.bloggingapp.users.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        UserEntity userEntity = modelMapper.map(createUserRequestDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return modelMapper.map(savedUserEntity, UserResponseDto.class);
    }

    public UserResponseDto verifyUser(LoginUserDto loginUserDto) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(loginUserDto.getUsername());
        if(userEntity == null) {
            throw new UserNotFoundException(loginUserDto.getUsername());
        }
        if(!passwordEncoder.matches(loginUserDto.getPassword(), userEntity.getPassword())) {
            throw new InvalidPasswordException();
        }
        return modelMapper.map(userEntity, UserResponseDto.class);
    }
}
