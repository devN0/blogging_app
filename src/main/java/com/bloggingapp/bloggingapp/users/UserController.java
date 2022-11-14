package com.bloggingapp.bloggingapp.users;

import com.bloggingapp.bloggingapp.commons.ErrorResponseDto;
import com.bloggingapp.bloggingapp.users.dtos.CreateUserRequestDto;
import com.bloggingapp.bloggingapp.users.dtos.LoginUserDto;
import com.bloggingapp.bloggingapp.users.dtos.UserResponseDto;
import com.bloggingapp.bloggingapp.users.exceptions.InvalidPasswordException;
import com.bloggingapp.bloggingapp.users.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(createUserRequestDto);
        return ResponseEntity.created(URI.create("/users/"+userResponseDto.getId())).body(userResponseDto);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> verifyUser(@RequestBody LoginUserDto loginUserDto) {
        UserResponseDto verifiedUser = userService.verifyUser(loginUserDto);
        return ResponseEntity.ok(verifiedUser);
    }

    @ExceptionHandler({
            InvalidPasswordException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleExceptions(Exception e) {
        if(e instanceof UserNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDto(e.getMessage()));
    }
}
