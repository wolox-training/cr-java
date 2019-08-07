package com.wolox.training.services;

import com.wolox.training.constants.ErrorMessages;
import com.wolox.training.exceptions.NotFoundException;
import com.wolox.training.exceptions.ServerErrorException;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
import com.wolox.training.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers(){
        try {
            return userRepository.findAll();
        }catch(ServerErrorException serverError){
            throw new ServerErrorException(ErrorMessages.internalServerErrorMessage);
        }
    }

    public User getUser(long id){

    }

    private User findById(long id){
        return userRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorMessages.notFoundBookErrorMessage));
    }

}
