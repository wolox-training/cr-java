package com.wolox.training.models;
import com.google.common.base.Preconditions;
import com.wolox.training.constants.ErrorMessages;
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
    @ApiModelProperty
    private String author;

    @Column(nullable = false)
    @ApiModelProperty
    private String image;

    @Column(nullable = false)
    @ApiModelProperty
    private String title;

    @Column(nullable = false)
    @ApiModelProperty
    private String subtitle;

    @Column(nullable = false)
    @ApiModelProperty
    private String publisher;

    @Column(nullable = false)
    @ApiModelProperty
    private String year;

    @Column(nullable = false)
    @ApiModelProperty
    private int pages;

    @Column(nullable = false,unique=true)
    @ApiModelProperty
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users;

    public Book(String genre, String author,String image, String title,String subtitle,
                String publisher,String year,int pages,String isbn) {
        this.genre = genre;
        this.author = Preconditions.checkNotNull(author,ErrorMessages.nullFieldErrorMessage);
        this.image = Preconditions.checkNotNull(image,ErrorMessages.nullFieldErrorMessage);;
        this.title = Preconditions.checkNotNull(title,ErrorMessages.nullFieldErrorMessage);;
        this.subtitle = Preconditions.checkNotNull(subtitle,ErrorMessages.nullFieldErrorMessage);;
        this.publisher = Preconditions.checkNotNull(publisher,ErrorMessages.nullFieldErrorMessage);;
        this.year = Preconditions.checkNotNull(year,ErrorMessages.nullFieldErrorMessage);;
        this.pages = Preconditions.checkNotNull(pages,ErrorMessages.nullFieldErrorMessage);
        this.isbn = Preconditions.checkNotNull(isbn,ErrorMessages.nullFieldErrorMessage);;
    }

    public Book(){
    }

    public Book(BookDTO bookDto) {
        this.genre = bookDto.getGenre();
        this.author =  Preconditions.checkNotNull(bookDto.getAuthor(),ErrorMessages.nullFieldErrorMessage);
        this.image =  Preconditions.checkNotNull(bookDto.getImage(),ErrorMessages.nullFieldErrorMessage);
        this.title =  Preconditions.checkNotNull(bookDto.getTitle(),ErrorMessages.nullFieldErrorMessage);
        this.subtitle =  Preconditions.checkNotNull(bookDto.getSubtitle(),ErrorMessages.nullFieldErrorMessage);
        this.publisher =  Preconditions.checkNotNull(bookDto.getPublisher(),ErrorMessages.nullFieldErrorMessage);
        this.year =  Preconditions.checkNotNull(bookDto.getYear(),ErrorMessages.nullFieldErrorMessage);
        this.pages = Preconditions.checkNotNull(bookDto.getPages(),ErrorMessages.nullFieldErrorMessage);
        this.isbn =  Preconditions.checkNotNull(bookDto.getIsbn(),ErrorMessages.nullFieldErrorMessage);
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
        this.author = Preconditions.checkNotNull(author,ErrorMessages.nullFieldErrorMessage);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = Preconditions.checkNotNull(image,ErrorMessages.nullFieldErrorMessage);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Preconditions.checkNotNull(title,ErrorMessages.nullFieldErrorMessage);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = Preconditions.checkNotNull(subtitle,ErrorMessages.nullFieldErrorMessage);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = Preconditions.checkNotNull(publisher,ErrorMessages.nullFieldErrorMessage);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = Preconditions.checkNotNull(year,ErrorMessages.nullFieldErrorMessage);
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = Preconditions.checkNotNull(pages,ErrorMessages.nullFieldErrorMessage);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = Preconditions.checkNotNull(isbn,ErrorMessages.nullFieldErrorMessage);
    }
}
