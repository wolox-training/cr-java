package com.wolox.training.services;
import com.wolox.training.exceptions.BadRequestException;
import com.wolox.training.exceptions.NotFoundException;
import com.wolox.training.exceptions.ServerErrorException;
import com.wolox.training.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wolox.training.models.Book;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Book createBook(Book book){
        if(!checkBookFields(book)){
            throw new BadRequestException("Invalid book fields");
        }
        try {
            return bookRepository.save(book);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException("Internal server error");
        }
    }

    public Book getBook(int id){
        return findById(id);
    }

    public Book getBookByAuthor(String author){
        Book book = bookRepository.findOneByAuthor(author);
        if(book==null){
            throw new NotFoundException("Book with author not found");
        }
        return book;
    }

    public List<Book> getBooks(){
        try {
            return bookRepository.findAll();
        }catch(ServerErrorException serverError){
            throw new ServerErrorException("Internal server error");
        }
    }

    public Book updateBook(int id, Book updatedBook){
        if(!checkBookFields(updatedBook)){
            throw new BadRequestException("Invalid book fields");
        }
        try {
            Book book = findById(id);
            book.setAuthor(updatedBook.getAuthor());
            book.setGenre(updatedBook.getGenre());
            book.setImage(updatedBook.getImage());
            book.setIsbn(updatedBook.getIsbn());
            book.setPages(updatedBook.getPages());
            book.setPublisher(updatedBook.getPublisher());
            book.setSubtitle(updatedBook.getSubtitle());
            book.setTitle(updatedBook.getTitle());
            book.setYear(updatedBook.getYear());
            return bookRepository.save(book);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException("Internal server error");
        }
    }

    public void deleteBook (int id){
        try {
            bookRepository.deleteById(id);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException("Internal server error");
        }
    }

    private Book findById(int id){
        return bookRepository.findById(id).orElseThrow(()->new NotFoundException("book not found"));
    }

    private boolean checkBookFields(Book book){
        if(book.getPublisher()==null || book.getIsbn()==null || book.getImage()==null
                || book.getAuthor()==null || book.getSubtitle()==null  || book.getTitle()==null || book.getYear()==null){
            return false;
        }
        return true;
    }
}
