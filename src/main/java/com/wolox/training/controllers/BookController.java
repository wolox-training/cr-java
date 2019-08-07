package com.wolox.training.controllers;
import com.wolox.training.dtos.BookDTO;
import com.wolox.training.models.Book;
import com.wolox.training.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    ModelMapper modelMapper= new ModelMapper();

    @Autowired
    private BookService bookService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name",required=false,defaultValue="World") String name, Model model){
        model.addAttribute("name",name);
        return "greeting";
    }

    @GetMapping("/book")
    public List<BookDTO> getBooks(){
        List<Book> books = bookService.getBooks();
        return books.stream().map(book -> convertToDto(book)).collect(Collectors.toList());
    }

    @PostMapping("/book")
    public BookDTO createBook(@RequestBody BookDTO bookDto){
        Book book = new Book(bookDto);
        Book createdBook = bookService.createBook(book);
        return convertToDto(createdBook);
    }

    @PutMapping("/book/{id}")
    public BookDTO updateBook(@PathVariable("id") int bookId, @RequestBody BookDTO bookDto){        Book requestBook = convertToEntity(bookDto);
        Book updatedBook = bookService.updateBook(bookId,requestBook);
        return convertToDto(updatedBook);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable("id") int bookId){
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
