package com.wolox.training.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wolox.training.constants.ErrorMessages;
import com.wolox.training.exceptions.BookAlreadyOwnException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class BookApiDTO implements Serializable {
    private String isbn;
    private String title;
    private String subtitle;
    private List<String> publishers = new ArrayList<String>();
    private String publishDate;
    private int numberOfPages;
    private List<String> authors = new ArrayList<String>();

    public BookApiDTO(){}

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void addAuthor(String author) {
        if (!this.authors.contains(author)) {
            this.authors.add(author);
        }
    }

    public void addPublisher(String publisher) {
        if (!this.publishers.contains(publisher)) {
            this.publishers.add(publisher);
        }
    }
}
