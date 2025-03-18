package ru.y_lab.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisplayName("Тесты для UserRepository")
public class UserRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        userRepository = new UserRepository(connection);

        userRepository.findAll().forEach(user -> userRepository.delete(user.getId()));
    }

    @Test
    @DisplayName("Сохранение пользователя")
    void testSave() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setAdmin(false);

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Поиск пользователя по email")
    void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setAdmin(false);

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getName());
    }

    @Test
    @DisplayName("Поиск пользователя по ID")
    void testFindById() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setAdmin(false);

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent());

        Optional<User> userById = userRepository.findById(foundUser.get().getId());
        assertTrue(userById.isPresent());
        assertEquals(foundUser.get().getId(), userById.get().getId());
    }

    @Test
    @DisplayName("Обновление пользователя")
    void testUpdate() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setAdmin(false);

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent());

        foundUser.get().setName("Updated User");
        userRepository.update(foundUser.get());

        Optional<User> updatedUser = userRepository.findByEmail("test@example.com");
        assertTrue(updatedUser.isPresent());
        assertEquals("Updated User", updatedUser.get().getName());
    }

    @Test
    @DisplayName("Удаление пользователя")
    void testDelete() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setAdmin(false);

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent());

        userRepository.delete(foundUser.get().getId());

        Optional<User> deletedUser = userRepository.findByEmail("test@example.com");
        assertFalse(deletedUser.isPresent());
    }

    @Test
    @DisplayName("Получение всех пользователей")
    void testFindAll() {
        User user1 = new User();
        user1.setEmail("test1@example.com");
        user1.setPassword("password1");
        user1.setName("Test User 1");
        user1.setAdmin(false);

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setPassword("password2");
        user2.setName("Test User 2");
        user2.setAdmin(true);

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }
}