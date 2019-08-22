package com.wolox.training.controllers;

import com.wolox.training.constants.SwaggerMessages;
import com.wolox.training.dtos.BookDTO;
import com.wolox.training.dtos.UserDTO;
import com.wolox.training.dtos.UserPageDTO;
import com.wolox.training.exceptions.ServerErrorException;
import com.wolox.training.models.User;
import com.wolox.training.security.IAuthenticationFacade;
import com.wolox.training.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Api
public class UserController {
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserService userService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/logged")
    @ApiOperation(value="Return user logged", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.findSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public UserDTO currentUserName() {
        String username = authenticationFacade.getAuthentication().getName();
        User user = userService.findByUsername(username);
        if(user!=null){
            return convertToDto(user);
        }
        throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
    }

    @GetMapping
    @ApiOperation(value="Return list of existing users", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.findSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public UserPageDTO getUsers(@RequestParam(value = "from", required = false) LocalDate from,
                                  @RequestParam(value = "to", required = false) LocalDate to,
                                  @RequestParam(value = "birthday", required = false) LocalDate birthday,
                                  @RequestParam(value = "username",required = false) String username,
                                  @RequestParam(value = "name", required = false) String name,
                                  Pageable pageable){
        Page<User> users = userService.getUsers(from,to,birthday,name,username,pageable);
        return convertToUserPageDto(users);
    }

    @GetMapping("/{id}")
    @ApiOperation(value="Giving an id, return the user", response = UserDTO.class)
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
    @ApiOperation(value="Create an user", response = UserDTO.class)
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
    @ApiOperation(value="Giving an id, update an user", response = UserDTO.class)
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
    @ApiOperation(value="Giving an id, delete an user", response = UserDTO.class)
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
    @ApiOperation(value="Giving an userId and a bookId, unlink user and book", response = UserDTO.class)
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

    private UserPageDTO convertToUserPageDto(Page<User>users){
        List<UserDTO> usersDto = users.stream().map(user -> convertToDto(user)).collect(Collectors.toList());
        UserPageDTO userPageDto = new UserPageDTO(usersDto,users.getNumberOfElements(),users.getTotalPages());
        return userPageDto;
    }

    private User convertToEntity(UserDTO userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
