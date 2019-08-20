package com.wolox.training.models;

import com.wolox.training.repositories.BookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @MockBean
    private Book oneTestBook;

    @Before
    public void setUp() {
        oneTestBook = new Book("genre", "author", "image", "title"
                , "subtitle", "publisher", "year", 2, "isbn");
    }

    @Test
    public void whenCreateBook_thenCheckCreated() throws Exception {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        if (bookRepository.findById((long) 1).isPresent()) {
            Book book = bookRepository.findById((long) 1).get();
            Assert.assertEquals(book.getAuthor(), oneTestBook.getAuthor());
            Assert.assertEquals(book.getImage(), oneTestBook.getImage());
            Assert.assertEquals(book.getTitle(), oneTestBook.getTitle());
            Assert.assertEquals(book.getSubtitle(), oneTestBook.getSubtitle());
            Assert.assertEquals(book.getPublisher(), oneTestBook.getPublisher());
            Assert.assertEquals(book.getYear(), oneTestBook.getYear());
            Assert.assertEquals(book.getPages(), oneTestBook.getPages());
            Assert.assertEquals(book.getIsbn(), oneTestBook.getIsbn());
            Assert.assertEquals(book.getId(), oneTestBook.getId());
        }
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateBookWithTitleNull_thenThrowException() {
        oneTestBook.setTitle(null);
        entityManager.persist(oneTestBook);
        entityManager.flush();
    }

    @Test
    public void whenUpdateBook_thenUpdated() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        if (bookRepository.findById((long) 1).isPresent()) {
            Book book = bookRepository.findById((long) 1).get();
            book.setTitle("Harry Potter");
            Book updatedBook = entityManager.persist(book);
            Assert.assertEquals(updatedBook.getTitle(), "Harry Potter");
        }
    }

    @Test
    public void whenDeleteBook_thenDeleted() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        bookRepository.delete(oneTestBook);
        Assert.assertEquals(false, bookRepository.findById((long) 1).isPresent());
    }

    @Test
    public void whenFindByPublisherAndGenreAndYear_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        List<Book> books = bookRepository.findByPublisherAndGenreAndYear("publisher"
                , "genre", "year");
        Book book = books.get(0);
        if (books!= null && book != null) {
            Assert.assertEquals(book.getAuthor(), oneTestBook.getAuthor());
            Assert.assertEquals(book.getImage(), oneTestBook.getImage());
            Assert.assertEquals(book.getTitle(), oneTestBook.getTitle());
            Assert.assertEquals(book.getSubtitle(), oneTestBook.getSubtitle());
            Assert.assertEquals(book.getPublisher(), oneTestBook.getPublisher());
            Assert.assertEquals(book.getYear(), oneTestBook.getYear());
            Assert.assertEquals(book.getPages(), oneTestBook.getPages());
            Assert.assertEquals(book.getIsbn(), oneTestBook.getIsbn());
            Assert.assertEquals(book.getId(), oneTestBook.getId());
        }
    }

}
