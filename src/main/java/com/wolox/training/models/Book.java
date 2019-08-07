package com.wolox.training.models;
import com.wolox.training.dtos.BookDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String genre;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String subtitle;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false)
    private String year;
    @Column(nullable = false)
    private int pages;
    @Column(nullable = false,unique=true)
    private String isbn;

    public Book(String genre, @NotNull String author,@NotNull String image, @NotNull String title,@NotNull String subtitle,
                @NotNull String publisher,@NotNull String year,@NotNull int pages,@NotNull String isbn) {
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