package com.wolox.training.models;

import com.wolox.training.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private User oneTestUser;

    @Before
    public void setUp(){
        LocalDate localDate = LocalDate.parse("1995-06-09");
        oneTestUser = new User("carlos","carlos","carlos", localDate);
        oneTestUser.setBooks(new ArrayList<Book>());
    }

    @Test
    public void whenCreateUser_thenCheckCreated() throws Exception {
        entityManager.persist(oneTestUser);
        entityManager.flush();
        if(userRepository.findById((long) 1).isPresent()){
            User user = userRepository.findById((long) 1).get();
            Assert.assertEquals(user.getUsername(),oneTestUser.getUsername());
            Assert.assertEquals(user.getName(),oneTestUser.getName());
            Assert.assertEquals(user.getBirthday(),oneTestUser.getBirthday());
        }
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateUserWithUsernameNull_thenThrowException() {
        oneTestUser.setUsername(null);
        entityManager.persist(oneTestUser);
        entityManager.flush();
    }

    @Test
    public void whenUpdateUser_thenUpdated() {
        entityManager.persist(oneTestUser);
        entityManager.flush();
        if(userRepository.findById((long) 1).isPresent()) {
            User user = userRepository.findById((long) 1).get();
            user.setName("jorge");
            User updatedUser = entityManager.persist(user);
            Assert.assertEquals(updatedUser.getName(), "jorge");
        }
    }

    @Test
    public void whenDeleteUser_thenDeleted() {
        entityManager.persist(oneTestUser);
        entityManager.flush();
        userRepository.delete(oneTestUser);
        Assert.assertEquals(false,userRepository.findById((long)1).isPresent());
    }

}
