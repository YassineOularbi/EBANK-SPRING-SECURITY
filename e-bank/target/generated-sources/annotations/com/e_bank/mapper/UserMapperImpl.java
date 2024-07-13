package com.e_bank.mapper;

import com.e_bank.dto.UserDto;
import com.e_bank.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-13T16:48:02+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setName( userDto.getName() );
        user.setUsername( userDto.getUsername() );
        user.setPassword( userDto.getPassword() );
        user.setMail( userDto.getMail() );
        user.setPhone( userDto.getPhone() );

        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.name( user.getName() );
        userDto.mail( user.getMail() );
        userDto.username( user.getUsername() );
        userDto.password( user.getPassword() );
        userDto.phone( user.getPhone() );

        return userDto.build();
    }

    @Override
    public List<User> toUsers(List<UserDto> userDtos) {
        if ( userDtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDtos.size() );
        for ( UserDto userDto : userDtos ) {
            list.add( toUser( userDto ) );
        }

        return list;
    }

    @Override
    public List<UserDto> toUserDtos(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( toUserDto( user ) );
        }

        return list;
    }

    @Override
    public User updateUserFromDto(UserDto userDto, User user) {
        if ( userDto == null ) {
            return user;
        }

        user.setName( userDto.getName() );
        user.setUsername( userDto.getUsername() );
        user.setPassword( userDto.getPassword() );
        user.setMail( userDto.getMail() );
        user.setPhone( userDto.getPhone() );

        return user;
    }
}
