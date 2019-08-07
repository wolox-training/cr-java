package com.wolox.training.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wolox.training.models.Book;

<<<<<<< HEAD
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
        Book findOneByAuthor(String author);
=======
public interface BookRepository extends JpaRepository<Book,Long> {
        Book findByAuthor(String author);
>>>>>>> book_repository
}
