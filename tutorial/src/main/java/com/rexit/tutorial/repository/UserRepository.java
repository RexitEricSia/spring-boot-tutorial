package com.rexit.tutorial.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rexit.tutorial.model.User;

import jakarta.persistence.LockModeType;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByAge(int age);

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    User findBySimilarEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM \"user\" WHERE age >= :age", nativeQuery = true)
    List<User> findByGreaterAge(@Param("age") int age);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsernameWithLock(@Param("username") String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByIDWithLock(@Param("id") Long id);
}
