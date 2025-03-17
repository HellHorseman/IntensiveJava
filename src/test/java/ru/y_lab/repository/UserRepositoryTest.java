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

        userRepository = new UserRepository();
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

        User foundUser = userRepository.findByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
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

        User foundUser = userRepository.findByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals("Test User", foundUser.getName());
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

        User foundUser = userRepository.findByEmail("test@example.com");
        User userById = userRepository.findById(foundUser.getId());

        assertNotNull(userById);
        assertEquals(foundUser.getId(), userById.getId());
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

        User foundUser = userRepository.findByEmail("test@example.com");
        foundUser.setName("Updated User");
        userRepository.update(foundUser);

        User updatedUser = userRepository.findByEmail("test@example.com");
        assertEquals("Updated User", updatedUser.getName());
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

        User foundUser = userRepository.findByEmail("test@example.com");
        userRepository.delete(foundUser.getId());

        User deletedUser = userRepository.findByEmail("test@example.com");
        assertNull(deletedUser);
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
