package com.wolox.training.models;
import com.wolox.training.dtos.BookDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@ApiModel
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @ApiModelProperty(notes = "The book genre: could be horror, comedy, drama, etc.")
    private String genre;

    @Column(nullable = false)
    @NotNull(message = "name cannot be null")
    @ApiModelProperty
    private String author;

    @Column(nullable = false)
    @NotNull(message = "image cannot be null")
    @ApiModelProperty
    private String image;

    @Column(nullable = false)
    @NotNull(message = "title cannot be null")
    @ApiModelProperty
    private String title;

    @Column(nullable = false)
    @NotNull(message = "subtitle cannot be null")
    @ApiModelProperty
    private String subtitle;

    @Column(nullable = false)
    @NotNull(message = "publisher cannot be null")
    @ApiModelProperty
    private String publisher;

    @Column(nullable = false)
    @NotNull(message = "year cannot be null")
    @ApiModelProperty
    private String year;

    @Column(nullable = false)
    @NotNull(message = "pages cannot be null")
    @ApiModelProperty
    private int pages;

    @Column(nullable = false,unique=true)
    @NotNull(message = "isbn cannot be null")
    @ApiModelProperty
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users;


    public Book(String genre, String author,String image, String title,String subtitle,
                String publisher,String year,int pages,String isbn) {
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }

    public Book(){
    }

    public Book(BookDTO bookDto) {
        this.genre = bookDto.getGenre();
        this.author = bookDto.getAuthor();
        this.image = bookDto.getImage();
        this.title = bookDto.getTitle();
        this.subtitle = bookDto.getSubtitle();
        this.publisher = bookDto.getPublisher();
        this.year = bookDto.getYear();
        this.pages = bookDto.getPages();
        this.isbn = bookDto.getIsbn();
    }

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
