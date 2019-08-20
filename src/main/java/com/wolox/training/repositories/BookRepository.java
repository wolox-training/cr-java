package com.wolox.training.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wolox.training.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
        Book findOneByAuthor(String author);
        Book findOneByIsbn(String isbn);

        @Query("SELECT b FROM Book b WHERE (:publisher is null or b.publisher = :publisher) and" +
                " (:genre is null or b.genre = :genre) and (:year is null or b.year = :year)")
        List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher,
                                                  @Param("genre") String genre,
                                                  @Param("year") String year);
}
