package com.wolox.training.models;

import com.wolox.training.exceptions.BookAlreadyOwnException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable=false)
    private String username;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private LocalDate birthday;
    @OneToMany(mappedBy="user")
    private List<Book> books = new ArrayList<Book>();

    public User(String username, String name, LocalDate birthday, List<Book> books) {
        this.username = username;
        this.name = name;
        this.birthday = birthday;
        this.books = books;
    }

    public User(String username, String name, LocalDate birthday) {
        this.username = username;
        this.name = name;
        this.birthday = birthday;
    }

    public User(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book){
        if(this.books.contains(book)){
            throw new BookAlreadyOwnException("User already has this book");
        }else{
            this.books.add(book);
        }
    }

    public void removeBook(Book book){
        this.books.removeIf(bookMapped->bookMapped.getId()==book.getId());
    }
}
