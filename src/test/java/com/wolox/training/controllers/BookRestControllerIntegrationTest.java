package com.wolox.training.controllers;

import com.wolox.training.constants.ErrorMessages;
import com.wolox.training.exceptions.NotFoundException;
import com.wolox.training.exceptions.ServerErrorException;
import com.wolox.training.models.Book;
import com.wolox.training.security.CustomAuthenticationProvider;
import com.wolox.training.services.BookService;
import com.wolox.training.services.OpenLibraryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookRestControllerIntegrationTest {

    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService mockBookService;

    @MockBean
    private OpenLibraryService openLibraryService;

    @MockBean
    private CustomAuthenticationProvider customAuthenticationProvider;

    private Book oneTestBook;

    @Before
    public void setUp(){
        LocalDate localDate = LocalDate.parse("1995-06-09");
        oneTestBook = new Book("fear","juan", "asdad123","the shinning","something"
                ,"the publishers","1990",200,"asd123");

    }

    @Test
    @WithMockUser(value = "spring")
    public void whenFindByIdWhichExists_thenBookIsReturned()
            throws Exception {
        Mockito.when(mockBookService.getBook(1L)).thenReturn(oneTestBook);
        String url = "/api/books/1";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":0,\"genre\":\"fear\",\"author\":\"juan\",\"image\":\"asdad123\"" +
                                ",\"title\":\"the shinning\",\"subtitle\":\"something\",\"publisher\":\"the publishers\"" +
                                ",\"year\":\"1990\",\"pages\":200,\"isbn\":\"asd123\"}"
                ));
    }

    @Test
    @WithMockUser(value = "spring")
    public void whenFindByAuthorWhichExists_thenBookIsReturned()
            throws Exception {
        Mockito.when(mockBookService.getBookByAuthor(any())).thenReturn(oneTestBook);
        String url = "/api/books/author/carlos";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":0,\"genre\":\"fear\",\"author\":\"juan\",\"image\":\"asdad123\"" +
                                ",\"title\":\"the shinning\",\"subtitle\":\"something\",\"publisher\":\"the publishers\"" +
                                ",\"year\":\"1990\",\"pages\":200,\"isbn\":\"asd123\"}"
                ));
    }

    @Test
    @WithMockUser(value = "spring")
    public void whenFindByIdWhichDoesNotExists_thenNotFoundErrorIsReturned()
            throws Exception {
        Mockito.when(mockBookService.getBook(1L))
                .thenThrow(new NotFoundException(ErrorMessages.notFoundUserErrorMessage));
        String url = "/api/books/1";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        "{\"status\":\"NOT_FOUND\",\"message\":\"User not found\"}"
                ));
    }

    @Test
    @WithMockUser(value = "spring")
    public void whenFindById_thenInternalServerErrorIsReturned()
            throws Exception {
        Mockito.when(mockBookService.getBook(1L))
                .thenThrow(new ServerErrorException(ErrorMessages.internalServerErrorMessage));
        String url = "/api/books/1";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(
                        "{\"status\":\"INTERNAL_SERVER_ERROR\",\"message\":\"Internal server error\"}"
                ));
    }

    @Test
    public void whenCreateBookAndRequestIsCorrect_thenCreateBook()
            throws Exception {
        Mockito.when(mockBookService.createBook( any(Book.class))).thenReturn(oneTestBook);
        String url = "/api/books";
        String bookRequest ="{\"id\":0,\"genre\":\"fear\",\"author\":\"juan\",\"image\":\"asdad123\"" +
                ",\"title\":\"the shinning\",\"subtitle\":\"something\",\"publisher\":\"the publishers\"" +
                ",\"year\":\"1990\",\"pages\":200,\"isbn\":\"asd123\"}";
        mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        "{\"id\":0,\"genre\":\"fear\",\"author\":\"juan\",\"image\":\"asdad123\"" +
                                ",\"title\":\"the shinning\",\"subtitle\":\"something\",\"publisher\":\"the publishers\"" +
                                ",\"year\":\"1990\",\"pages\":200,\"isbn\":\"asd123\"}"
                ));
    }

    @Test
    public void whenCreateBookAndServerError_thenServerError()
            throws Exception {
        Mockito.when(mockBookService.createBook( any(Book.class))).thenThrow(new ServerErrorException(ErrorMessages.internalServerErrorMessage));
        String url = "/api/books";
        String bookRequest ="{\"id\":0,\"genre\":\"fear\",\"author\":\"juan\",\"image\":\"asdad123\"" +
                ",\"title\":\"the shinning\",\"subtitle\":\"something\",\"publisher\":\"the publishers\"" +
                ",\"year\":\"1990\",\"pages\":200,\"isbn\":\"asd123\"}";
        mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookRequest))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(
                        "{\"status\":\"INTERNAL_SERVER_ERROR\",\"message\":\"Internal server error\"}"
                ));
    }

   @Test
   @WithMockUser(value = "spring")
   public void whenUpdateBookAndRequestIsCorrect_thenUpdateBook()
            throws Exception {
        Mockito.when(mockBookService.updateBook(anyLong(), any(Book.class))).thenReturn(oneTestBook);
        String url = "/api/books/1";
        String bookRequest ="{\"id\":0,\"genre\":\"fear\",\"author\":\"juan\",\"image\":\"asdad123\"" +
                ",\"title\":\"the shinning\",\"subtitle\":\"something\",\"publisher\":\"the publishers\"" +
                ",\"year\":\"1990\",\"pages\":200,\"isbn\":\"asd123\"}";
        mvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookRequest))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0,\"genre\":\"fear\",\"author\":\"juan\",\"image\":\"asdad123\"" +
                        ",\"title\":\"the shinning\",\"subtitle\":\"something\",\"publisher\":\"the publishers\"" +
                        ",\"year\":\"1990\",\"pages\":200,\"isbn\":\"asd123\"}"));
    }

    @Test
    @WithMockUser(value = "spring")
    public void whenDeleteBookAndRequestIsCorrect_thenDeletedBook()
            throws Exception {
        Mockito.doNothing().when(mockBookService).deleteBook(1L);
        String url = "/api/books/1";
        mvc.perform(
                delete(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
