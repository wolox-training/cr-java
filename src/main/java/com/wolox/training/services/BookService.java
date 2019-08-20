package com.wolox.training.services;
import com.wolox.training.constants.ErrorMessages;
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
        try {
            return bookRepository.save(book);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public Book getBook(long id){
        return findById(id);
    }

    public Book getBookByAuthor(String author){
        Book book = bookRepository.findOneByAuthor(author);
        if(book==null){
            throw new NotFoundException(ErrorMessages.notFoundBookErrorMessage);
        }
        return book;
    }

    public Book getBookByIsbn(String isbn){
        return bookRepository.findOneByIsbn(isbn);
    }

    public List<Book> getBooks(){
        try {
            return bookRepository.findAll();
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public Book updateBook(long id, Book updatedBook){
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
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public void deleteBook (long id){
        try {
            bookRepository.deleteById(id);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    private Book findById(long id){
        return bookRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorMessages.notFoundBookErrorMessage));
    }
}
