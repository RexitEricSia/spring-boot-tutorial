package com.rexit.tutorial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rexit.tutorial.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByAge(int age);

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    User findBySimilarEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM \"user\" WHERE age >= :age", nativeQuery = true)
    List<User> findByGreaterAge(@Param("age") int age);
}
