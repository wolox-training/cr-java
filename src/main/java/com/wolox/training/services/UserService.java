package com.wolox.training.services;

import com.wolox.training.constants.ErrorMessages;
import com.wolox.training.exceptions.NotFoundException;
import com.wolox.training.exceptions.ServerErrorException;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.repositories.UserRepository;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    public List<User> getUsers(){
        try {
            return userRepository.findAll();
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public User getUser(long id){
        return findUserById(id);
    }

    public User createUser(User user){
        try {
            return userRepository.save(user);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public User updateUser(long id,User updatedUser){
        try {
            User userFound = findUserById(id);
            userFound.setBirthday(updatedUser.getBirthday());
            userFound.setName(updatedUser.getName());
            userFound.setUsername(updatedUser.getUsername());
            return userRepository.save(userFound);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public User addBook(long userId,long bookId){
        try{
            User user = findUserById(userId);
            Book book = findBookById(bookId);
            user.addBook(book);
            return userRepository.save(user);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public User removeBook(long userId, long bookId){
        try{
            User user = findUserById(userId);
            Book book = findBookById(bookId);
            user.removeBook(book);
            return userRepository.save(user);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public void deleteUser (long id){
        try {
            userRepository.deleteById(id);
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    private User findUserById(long userId){
        return userRepository.findById(userId).orElseThrow(()->new NotFoundException(ErrorMessages.notFoundUserErrorMessage));
    }

    private Book findBookById(long bookId){
        return bookRepository.findById(bookId).orElseThrow(()->new NotFoundException(ErrorMessages.notFoundBookErrorMessage));
    }
}
