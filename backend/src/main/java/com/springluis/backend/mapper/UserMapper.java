package com.springluis.backend.mapper;

import org.springframework.stereotype.Component;

import com.springluis.backend.model.dto.UserDto;
import com.springluis.backend.model.entity.User;

@Component("userMapper")
public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto toTarget(User source) {
        if (source == null) {
            return null;
        }
        return UserDto.builder()
                .id(source.getId())
                .avatar(source.getAvatar())
                .username(source.getUsername())
                .email(source.getEmail())
                .bio(source.getBio())
                .key(source.getKey())
                .roles(source.getRoles())
                .createdAt(source.getCreatedAt())
                .build();
    }

    @Override
    public User toSource(UserDto target) {
        if (target == null) {
            return null;
        }
        return User.builder()
                .id(target.getId())
                .avatar(target.getAvatar())
                .username(target.getUsername())
                .email(target.getEmail())
                .bio(target.getBio())
                .key(target.getKey())
                .roles(target.getRoles())
                .createdAt(target.getCreatedAt())
                .build();   
    }

}
