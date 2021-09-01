package com.taskagile.infrastructure.repository;

import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class HibernateUserRepositoryTests {

    @TestConfiguration
    public static class UserRepositoryTestContextConfiguration {

        @Bean
        public UserRepository userRepository(EntityManager entityManager) {
            return new HibernateUserRepository(entityManager);
        }
    }

    @Autowired
    private UserRepository repository;

    @Test
    void save_nullUsernameUser_shouldFail() {
        User invalidUser = User.create(null, "email@email.com", "Password!");
        assertThrows(PersistenceException.class, () -> repository.save(invalidUser));
    }

    @Test
    void save_nullEmailAddressUser_shouldFail() {
        User invalidUser = User.create("username1", null, "Password!");
        assertThrows(PersistenceException.class, () -> repository.save(invalidUser));
    }

    @Test
    void save_nullPasswordUser_shouldFail() {
        User invalidUser = User.create("username1", "email@email.com", null);
        assertThrows(PersistenceException.class, () -> repository.save(invalidUser));
    }

    @Test
    void save_validUser_shouldSuccess() {
        String username = "username";
        String emailAddress = "email@email.com";
        User newUser = User.create(username, emailAddress, "MyPassword");
        repository.save(newUser);

        assertNotNull(newUser.getId(), "New user's id should be generated");
        assertNotNull(newUser.getCreatedDate(), "New user's created date should be generated");
        assertEquals(newUser.getUsername(), username);
        assertEquals(newUser.getEmailAddress(), emailAddress);
        assertEquals(newUser.getFirstName(), "");
        assertEquals(newUser.getLastName(), "");
    }

    @Test
    void save_usernameAlreadyExist_shouldFail() {
        String username = "username";
        String emailAddress = "email@email.com";
        User alreadyExistUser = User.create(username, emailAddress, "MyPassword!");
        repository.save(alreadyExistUser);

        try {
            User newUser = User.create(username, "new@email.com", "MyPassword!");
            repository.save(newUser);
        } catch (Exception e) {
            assertEquals(ConstraintViolationException.class.toString(), e.getCause().getClass().toString());
        }
    }

    @Test
    void save_emailAddressAlreadyExist_shouldFail() {
        String username = "username";
        String emailAddress = "email@email.com";
        User alreadyExistUser = User.create(username, emailAddress, "MyPassword!");
        repository.save(alreadyExistUser);

        try {
            User newUser = User.create("new", emailAddress, "MyPassword!");
            repository.save(newUser);
        } catch (Exception e) {
            assertEquals(ConstraintViolationException.class.toString(), e.getCause().getClass().toString());
        }
    }

    @Test
    void findByEmailAddress_exist_shouldReturnResult() {
        String emailAddress = "username@email.com";
        String username = "username";
        User newUser = User.create(username, emailAddress, "MyPassword!");
        repository.save(newUser);

        User found = repository.findByEmailAddress(emailAddress);
        assertEquals(username, found.getUsername(), "Username should match");
    }

    @Test
    void findByUsername_exist_shouldReturnResult() {
        String username = "username";
        String emailAddress = "username@email.com";
        User newUser = User.create(username, emailAddress, "MyPassword!");
        repository.save(newUser);

        User found = repository.findByUsername(username);
        assertEquals(found.getEmailAddress(), emailAddress, "Email address should match");
    }

    @Test
    void findByUsername_notExist_shouldReturnEmptyResult() {
        String username = "username";
        User user = repository.findByUsername(username);
        assertNull(user, "No user should by found");
    }

}
