package com.wolox.training.controllers;

import com.wolox.training.dtos.BookDTO;
import com.wolox.training.dtos.UserDTO;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
import com.wolox.training.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserService userService;

    @GetMapping('/users')
    public UserDTO getUsers(){

    }

    private UserDTO convertToDto(User user){
        UserDTO userDto = modelMapper.map(user,UserDTO.class);
        return userDto;
    }

    private User convertToEntity(UserDTO userDto){
        User user = modelMapper.map(userDto,User.class);
        return user;
    }

}
