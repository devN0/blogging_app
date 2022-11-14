package com.bloggingapp.bloggingapp.users.mappers;

import com.bloggingapp.bloggingapp.users.UserEntity;
import com.bloggingapp.bloggingapp.users.dtos.CreateUserRequestDto;
import com.bloggingapp.bloggingapp.users.dtos.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity mapCreateUserRequestDtoToUserEntity(CreateUserRequestDto createUserRequestDto);
    UserResponseDto mapUserEntityToUserResponseDto(UserEntity userEntity);
}
