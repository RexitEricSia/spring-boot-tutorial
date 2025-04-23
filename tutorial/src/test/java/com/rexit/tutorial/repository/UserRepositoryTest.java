package com.rexit.tutorial.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.rexit.tutorial.model.User;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createUser(String email, String userName, int age) {
        User user = User.builder().email(email).username(userName).age(age).build();
        return userRepository.save(user);
    }

    @Test
    @DisplayName("Test findByAge returns users with matching age")
    void testFindByAge() {
        createUser("alice@example.com", "alice", 30);
        createUser("bob@example.com", "bob", 25);

        List<User> result = userRepository.findByAge(30);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    @DisplayName("Test findBySimilarEmail returns a user with similar email")
    void testFindBySimilarEmail() {
        createUser("john.doe@example.com", "john", 40);

        User result = userRepository.findBySimilarEmail("john.doe");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    @DisplayName("Test findByGreaterAge returns users with age greater than or equal")
    void testFindByGreaterAge() {
        createUser("a@example.com", "a", 20);
        createUser("b@example.com", "b", 30);
        createUser("c@example.com", "c",  40);

        List<User> result = userRepository.findByGreaterAge(30);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(User::getEmail)
                .containsExactlyInAnyOrder("b@example.com", "c@example.com");
    }
}