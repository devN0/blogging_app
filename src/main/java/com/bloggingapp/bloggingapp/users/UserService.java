package com.bloggingapp.bloggingapp.users;

import com.bloggingapp.bloggingapp.security.authtoken.AuthTokenService;
import com.bloggingapp.bloggingapp.security.jwt.JwtService;
import com.bloggingapp.bloggingapp.users.dtos.CreateUserRequestDto;
import com.bloggingapp.bloggingapp.users.dtos.LoginUserDto;
import com.bloggingapp.bloggingapp.users.dtos.UserResponseDto;
import com.bloggingapp.bloggingapp.users.exceptions.InvalidPasswordException;
import com.bloggingapp.bloggingapp.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;
    private final JwtService jwtService;

    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        UserEntity userEntity = modelMapper.map(createUserRequestDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntity);

        // creating token for new user
//        String token = authTokenService.createToken(savedUserEntity);
        String token = jwtService.createJwt(savedUserEntity.getUsername());

        UserResponseDto userResponseDto = modelMapper.map(savedUserEntity, UserResponseDto.class);
        userResponseDto.setToken(token);
        return userResponseDto;
    }

    public UserResponseDto verifyUser(LoginUserDto loginUserDto) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(loginUserDto.getUsername());
        if(userEntity == null) {
            throw new UserNotFoundException(loginUserDto.getUsername());
        }
        if(!passwordEncoder.matches(loginUserDto.getPassword(), userEntity.getPassword())) {
            throw new InvalidPasswordException();
        }
        UserResponseDto userResponseDto = modelMapper.map(userEntity, UserResponseDto.class);
//        creating new token for existing user
        String authToken = authTokenService.createToken(userEntity);
        userResponseDto.setToken(authToken);
        return userResponseDto;
    }

    public UserResponseDto getUserFromUsername(String username) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        return modelMapper.map(userEntity, UserResponseDto.class);
    }
}
