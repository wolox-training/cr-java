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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

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
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams("publisher"
                , "genre", "year",null,null,null,null,null,null,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByOnlyPublisher_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams("publisher"
                , null, null,null,null,null,null,null,null,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByOnlyGenre_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams(null
                , "genre", null,null,null,null,null,null,null,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByOnlyYear_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams(null
                , null, "year",null,null,null,null,null,null,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByOnlyAuthor_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams(null
                , null, null,"author",null,null,null,null,null,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByOnlyImage_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams(null
                , null, null,null,"image",null,null,null,null,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByOnlyTitle_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams(null
                , null, null,null,null,"title",null,null,null,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByTitleAndSubtitle_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams(null
                , null, null,null,null,"title","subtitle",null,null,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByIsbn_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams(null
                , null, null,null,null,null,null,"isbn",null, pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

    @Test
    public void whenFindByPages_thenSuccess() {
        entityManager.persist(oneTestBook);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAllByParams(null
                , null, null,null,null,null,null,null,2,pageable);
        Book book = books.getContent().get(0);
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
            Assert.assertEquals(books.getTotalElements(),1);
            Assert.assertEquals(books.getTotalPages(),1);
        }
    }

}
