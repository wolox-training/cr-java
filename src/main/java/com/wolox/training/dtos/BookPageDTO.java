package com.wolox.training.dtos;

import java.util.ArrayList;
import java.util.List;

public class BookPageDTO {
    private List<BookDTO> books = new ArrayList<BookDTO>();
    private int numberOfElements;
    private int numberOfPages;

    public BookPageDTO(List<BookDTO> books, int numberOfElements, int numberOfPages) {
        this.books = books;
        this.numberOfElements = numberOfElements;
        this.numberOfPages = numberOfPages;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
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
