package com.wolox.training.models;

import com.wolox.training.repositories.UserRepository;
import com.wolox.training.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.rmi.server.ExportException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        oneTestUser = new User("carlos","carlos", localDate);
        oneTestUser.setBooks(new ArrayList<Book>());
    }


    @Test
    public void whenFindById_thenReturnUser() throws Exception {
        //Mockito.when(userRepository.findById(1L).get()).thenReturn(oneTestUser);
        entityManager.persist(oneTestUser);
        entityManager.flush();
        User user = userRepository.findById((long) 0).get();

        Assert.assertEquals(user.getUsername(),oneTestUser.getUsername());

    }



}
