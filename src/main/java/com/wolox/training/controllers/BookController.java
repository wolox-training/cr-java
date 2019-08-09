package com.wolox.training.controllers;
import com.wolox.training.constants.SwaggerMessages;
import com.wolox.training.dtos.BookDTO;
import com.wolox.training.models.Book;
import com.wolox.training.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {
    ModelMapper modelMapper= new ModelMapper();

    @Autowired
    private BookService bookService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name",required=false,defaultValue="World") String name, Model model){
        model.addAttribute("name",name);
        return "greeting";
    }

    @GetMapping
    @ApiOperation(value="Return list of existing books", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.findSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public List<BookDTO> getBooks(){
        List<Book> books = bookService.getBooks();
        return books.stream().map(book -> convertToDto(book)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value="Giving an id, return the book", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.findSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public BookDTO getBook(@PathVariable("id") long bookId){
        Book book = bookService.getBook(bookId);
        return convertToDto(book);
    }

    @GetMapping("/author/{author}")
    @ApiOperation(value="Giving an author name, return one book", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.findSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public BookDTO getBookByAuthor(@PathVariable("author") String bookAuthor){
        Book book = bookService.getBookByAuthor(bookAuthor);
        return convertToDto(book);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Create a book", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.createSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public BookDTO createBook(@RequestBody BookDTO bookDto){
        Book book = convertToEntity(bookDto);
        Book createdBook = bookService.createBook(book);
        return convertToDto(createdBook);
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Giving an id, update a book", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.updateSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public BookDTO updateBook(@PathVariable("id") long bookId, @RequestBody BookDTO bookDto){
        Book requestBook = convertToEntity(bookDto);
        Book updatedBook = bookService.updateBook(bookId,requestBook);
        return convertToDto(updatedBook);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Giving an id, delete a book", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerMessages.deleteSuccess),
            @ApiResponse(code = 400, message = SwaggerMessages.badRequest),
            @ApiResponse(code = 404, message = SwaggerMessages.notFound),
            @ApiResponse(code = 500, message = SwaggerMessages.internalServerError)
    })
    public void deleteBook(@PathVariable("id") long bookId){
        bookService.deleteBook(bookId);
    }

    private BookDTO convertToDto(Book book){
        BookDTO bookDto = modelMapper.map(book,BookDTO.class);
        return bookDto;
    }

    private Book convertToEntity(BookDTO bookDto){
        Book book = modelMapper.map(bookDto,Book.class);
        return book;
    }
}
