package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.model.User;
import ru.y_lab.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisplayName("Тесты для UserService")
public class UserServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        userRepository = new UserRepository(connection);

        userService = new UserService(userRepository);

        userRepository.findAll().forEach(user -> userRepository.delete(user.getId()));
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    void testRegisterUser() {
        assertTrue(userService.registerUser("test@example.com", "password", "Test User", false));
        assertFalse(userService.registerUser("test@example.com", "password", "Test User", false));
    }

    @Test
    @DisplayName("Вход пользователя в систему")
    void testLogin() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        assertNotNull(userService.login("test@example.com", "password"));
        assertNull(userService.login("test@example.com", "wrongpassword"));
        assertNull(userService.login("unknown@example.com", "password"));
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    void testUpdateUser() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        assertTrue(userService.updateUser("test@example.com", "New Name", "newpassword"));
        User user = userService.login("test@example.com", "newpassword");
        assertNotNull(user);
        assertEquals("New Name", user.getName());
        assertFalse(userService.updateUser("unknown@example.com", "New Name", "newpassword"));
    }

    @Test
    @DisplayName("Обновление email пользователя")
    void testUpdateUserEmail() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        assertTrue(userService.updateUserEmail("test@example.com", "new@example.com"));
        User user = userService.login("new@example.com", "password");
        assertNotNull(user);
        assertEquals("new@example.com", user.getEmail());
        assertFalse(userService.updateUserEmail("unknown@example.com", "new@example.com"));
    }

    @Test
    @DisplayName("Блокировка/разблокировка пользователя")
    void testToggleUserBlock() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        User user = userService.login("test@example.com", "password");
        assertNotNull(user);

        assertTrue(userService.toggleUserBlock(user.getId()));
        User blockedUser = userService.login("test@example.com", "password");
        assertTrue(blockedUser.isAdmin());

        assertTrue(userService.toggleUserBlock(user.getId()));
        User unblockedUser = userService.login("test@example.com", "password");
        assertFalse(unblockedUser.isAdmin());

        assertFalse(userService.toggleUserBlock(999L));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void testDeleteUser() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        User user = userService.login("test@example.com", "password");
        assertNotNull(user);

        assertTrue(userService.deleteUser(user.getId()));
        assertNull(userService.login("test@example.com", "password"));

        assertFalse(userService.deleteUser(999L));
    }

    @Test
    @DisplayName("Получение списка всех пользователей")
    void testGetAllUsers() {
        userService.registerUser("user1@example.com", "password1", "User One", false);
        userService.registerUser("user2@example.com", "password2", "User Two", true);
        Map<String, User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.containsKey("user1@example.com"));
        assertTrue(users.containsKey("user2@example.com"));
    }
}
