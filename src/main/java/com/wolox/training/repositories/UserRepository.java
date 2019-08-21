package com.wolox.training.repositories;
import com.wolox.training.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findOneByUsername(String username);
    List<User> findByBirthdayBetweenAndNameContainingIgnoreCase(LocalDate from,LocalDate to,String words);
}
