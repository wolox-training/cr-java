package com.wolox.training.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wolox.training.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
        Book findOneByAuthor(String author);
        Book findOneByIsbn(String isbn);
        List<Book> findByPublisherAndGenreAndYear(String publisher,String genre,String year);
}
