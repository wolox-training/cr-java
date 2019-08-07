package com.wolox.training.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
        Book findByAuthor(String author);
}
