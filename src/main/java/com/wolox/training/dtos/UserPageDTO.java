package com.wolox.training.dtos;

import java.util.ArrayList;
import java.util.List;

public class UserPageDTO {
    private List<UserDTO> users = new ArrayList<UserDTO>();
    private int numberOfElements;
    private int numberOfPages;

    public UserPageDTO(List<UserDTO> users, int numberOfElements, int numberOfPages) {
        this.users = users;
        this.numberOfElements = numberOfElements;
        this.numberOfPages = numberOfPages;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
