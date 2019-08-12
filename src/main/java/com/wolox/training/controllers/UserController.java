package com.wolox.training.controllers;

import com.wolox.training.constants.ErrorMessages;
import com.wolox.training.constants.SwaggerMessages;
import com.wolox.training.dtos.BookDTO;
import com.wolox.training.dtos.UserDTO;
import com.wolox.training.exceptions.BadRequestException;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
import com.wolox.training.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Api
public class UserController {
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserService userService;

    @GetMapping
    @ApiOperation(value="Return list of existing users", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.findSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public List<UserDTO> getUsers(){
        List<User> users = userService.getUsers();
        return users.stream().map(user -> convertToDto(user)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value="Giving an id, return the user", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.findSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public UserDTO getUser(@PathVariable("id") long userId){
        User user = userService.getUser(userId);
        return convertToDto(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Create an user", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.createSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    @CrossOrigin
    public UserDTO createUser(@RequestBody UserDTO userDto) {
        User user = convertToEntity(userDto);
        User createdUser = userService.createUser(user);
        return convertToDto(createdUser);
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Giving an id, update an user", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.updateSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public UserDTO updateUser(@PathVariable("id") long userId, @RequestBody UserDTO userDto){
        User user = convertToEntity(userDto);
        User updatedUser = userService.updateUser(userId,user);
        return convertToDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Giving an id, delete an user", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.deleteSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public void deleteUser(@PathVariable("id") long userId){
        userService.deleteUser(userId);
    }

    @PostMapping("/{userId}/book/{bookId}")
    @ApiOperation(value="Giving an userId and a bookId, link user and book (an user can not have the same book twice)",
            response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.linkSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public UserDTO addBook(@PathVariable("userId") long userId, @PathVariable("bookId") long bookId){
        User user = userService.addBook(userId,bookId);
        return convertToDto(user);
    }

    @DeleteMapping("/{userId}/book/{bookId}")
    @ApiOperation(value="Giving an userId and a bookId, unlink user and book", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.unlinkSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public UserDTO removeBook(@PathVariable("userId") long userId, @PathVariable("bookId") long bookId){
        User user = userService.removeBook(userId,bookId);
        return convertToDto(user);
    }

    private UserDTO convertToDto(User user){
        UserDTO userDto = modelMapper.map(user,UserDTO.class);
        return userDto;
    }

    private User convertToEntity(UserDTO userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
