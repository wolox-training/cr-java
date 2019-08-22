package com.wolox.training.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wolox.training.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book,Long> {
        Book findOneByAuthor(String author);
        Book findOneByIsbn(String isbn);

        @Query("SELECT b FROM Book b WHERE (:publisher is null or b.publisher = :publisher) and" +
                " (:genre is null or b.genre = :genre) and (:year is null or b.year = :year) and" +
                "(:author is null or b.author = :author) and (:image is null or b.image = :image) and" +
                "(:title is null or b.title = :title) and (:subtitle is null or b.subtitle = :subtitle) and" +
                "(:isbn is null or b.isbn = :isbn) and (:pages is null or b.pages = :pages)")
        Page<Book> findAllByParams(@Param("publisher") String publisher,
                                   @Param("genre") String genre,
                                   @Param("year") String year,
                                   @Param("author") String author,
                                   @Param("image") String image,
                                   @Param("title") String title,
                                   @Param("subtitle") String subtitle,
                                   @Param("isbn") String isbn,
                                   @Param("pages") Integer pages,
                                   Pageable pageable);
}
