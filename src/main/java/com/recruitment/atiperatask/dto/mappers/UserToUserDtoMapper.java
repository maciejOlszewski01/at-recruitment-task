package com.recruitment.atiperatask.dto.mappers;


import com.recruitment.atiperatask.dto.UserDto;
import com.recruitment.atiperatask.model.User;

public class UserToUserDtoMapper {
    public static UserDto map(User user){
         UserDto userDto = new UserDto(
                 user.getUsername(),
                 user.getRepositoryList().stream().map(RepositoryToRepositoryDtoMapper::map).toList()
        );
         return userDto;
    }
}
