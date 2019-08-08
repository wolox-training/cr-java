package com.wolox.training.controllers;

import com.wolox.training.dtos.BookDTO;
import com.wolox.training.dtos.UserDTO;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
import com.wolox.training.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        List<User> users = userService.getUsers();
        return users.stream().map(user -> convertToDto(user)).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable("id") long userId){
        User user = userService.getUser(userId);
        return convertToDto(user);
    }

    @PostMapping("/users")
    public UserDTO createUser(@RequestBody UserDTO userDto){
        User user = convertToEntity(userDto);
        User createdUser = userService.createUser(user);
        return convertToDto(createdUser);
    }

    @PutMapping("/users/{id}")
    public UserDTO updateUser(@PathVariable("id") long userId, @RequestBody UserDTO userDto){
        User user = convertToEntity(userDto);
        User updatedUser = userService.updateUser(userId,user);
        return convertToDto(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") long userId){
        userService.deleteUser(userId);
    }

    @PostMapping("/users/{userId}/book/{bookId}")
    public UserDTO addBook(@PathVariable("userId") long userId, @PathVariable("bookId") long bookId){
        User user = userService.addBook(userId,bookId);
        return convertToDto(user);
    }

    @DeleteMapping("/users/{userId}/book/{bookId}")
    public UserDTO removeBook(@PathVariable("userId") long userId, @PathVariable("bookId") long bookId){
        User user = userService.removeBook(userId,bookId);
        return convertToDto(user);
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
