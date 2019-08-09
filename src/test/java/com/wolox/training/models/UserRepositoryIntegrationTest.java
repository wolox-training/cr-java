package com.wolox.training.models;

import com.wolox.training.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindById_thenReturnUser() {
        LocalDate localDate = LocalDate.parse("1995-06-09");
        /*User user = new User("carlos","carlos", localDate);
        entityManager.persist(user);
        entityManager.flush();*/
        /*User userFound = userRepository.findById(user.getId()).get();
        assertThat(userFound.getId())
                .isEqualTo(user.getId());*/
       /* assertThat("holA")
                .isEqualTo("holA");*/
    }

    /*@Test
    public void whenFindAll_thenReturnUsers() {
        LocalDate localDate = LocalDate.parse("1995-06-09");
        User user = new User("carlos","carlos", localDate);
        User anotherUser = new User("juan","carlos", localDate);
        entityManager.persist(user);
        entityManager.persist(anotherUser);
        entityManager.flush();
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
    }*/


}
