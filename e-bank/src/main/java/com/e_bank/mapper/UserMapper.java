package com.e_bank.mapper;

import com.e_bank.dto.UserDto;
import com.e_bank.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
    List<User> toUsers(List<UserDto> userDtos);
    List<UserDto> toUserDtos(List<User> users);
    User updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
