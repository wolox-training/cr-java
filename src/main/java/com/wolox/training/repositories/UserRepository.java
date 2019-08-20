package com.wolox.training.repositories;
import com.wolox.training.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findOneByUsername(String username);
    @Query("SELECT u FROM User u WHERE (:from is null or u.birthday >= :from) and (:to is null or u.birthday <= :to)" +
            "and (:birthday is null or u.birthday = :birthday)"+
            "and (:name is null or UPPER(u.name) like concat('%',UPPER(cast(:name as text)),'%'))" +
            "and (:username is null or u.username = :username)")
    List<User> findAllByParams(@Param("from")LocalDate from,
                               @Param("to")LocalDate to,
                               @Param("birthday")LocalDate birthday,
                               @Param("name")String name,
                               @Param("username")String username);
}
