package com.wolox.training.controllers;

import com.google.gson.Gson;
import com.wolox.training.constants.ErrorMessages;
import com.wolox.training.dtos.UserDTO;
import com.wolox.training.exceptions.NotFoundException;
import com.wolox.training.exceptions.ServerErrorException;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.wolox.training.services.UserService;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserRestControllerIntegrationTest {
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService mockUserService;
    private User oneTestUser;
    private UserDTO oneTestUserDto;

    @Before
    public void setUp(){
        LocalDate localDate = LocalDate.parse("1995-06-09");
        oneTestUser = new User("carlos","carlos", localDate);
        oneTestUser.setBooks(new ArrayList<Book>());
        oneTestUserDto = modelMapper.map(oneTestUser, UserDTO.class);
    }

    @Test
    public void whenFindByIdWhichExists_thenUserIsReturned()
            throws Exception {
        Mockito.when(mockUserService.getUser(1L)).thenReturn(oneTestUser);
        String url = "/api/users/1";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                "{\"id\":0,\"username\":\"carlos\",\"name\":\"carlos\"}"
            ));
    }

    @Test
    public void whenFindByIdWhichDoesNotExists_thenNotFoundErrorIsReturned()
            throws Exception {
        Mockito.when(mockUserService.getUser(1L))
                .thenThrow(new NotFoundException(ErrorMessages.notFoundUserErrorMessage));
        String url = "/api/users/1";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"status\":\"NOT_FOUND\",\"message\":\"User not found\"}"
                ));
    }

    @Test
    public void whenFindById_thenInternalServerErrorIsReturned()
            throws Exception {
        Mockito.when(mockUserService.getUser(1L))
                .thenThrow(new ServerErrorException(ErrorMessages.internalServerErrorMessage));
        String url = "/api/users/1";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(
                        "{\"status\":\"INTERNAL_SERVER_ERROR\",\"message\":\"Internal server error\"}"
                ));
    }

    @Test
    public void whenCreateUserAndRequestIsCorrect_thenCreateUser()
        throws Exception {
        Mockito.when(mockUserService.createUser(oneTestUser)).thenReturn(oneTestUser);
        String url = "/api/users";
        Gson gson = new Gson();
        String json = gson.toJson(oneTestUser);
        mvc.perform(
                post(url)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        "{\"id\":0,\"username\":\"carlos\",\"name\":\"carlos\"}"
                ));
    }

}
