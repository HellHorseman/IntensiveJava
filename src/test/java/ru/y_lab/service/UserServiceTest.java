package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.y_lab.model.User;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testRegisterUser() {
        assertTrue(userService.registerUser("test@example.com", "password", "Test User", false));
        assertFalse(userService.registerUser("test@example.com", "password", "Test User", false));
    }

    @Test
    void testLogin() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        assertNotNull(userService.login("test@example.com", "password"));
        assertNull(userService.login("test@example.com", "wrongpassword"));
        assertNull(userService.login("unknown@example.com", "password"));
    }

    @Test
    void testUpdateUser() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        assertTrue(userService.updateUser("test@example.com", "New Name", "newpassword"));
        User user = userService.login("test@example.com", "newpassword");
        assertNotNull(user);
        assertEquals("New Name", user.getName());
        assertFalse(userService.updateUser("unknown@example.com", "New Name", "newpassword"));
    }

    @Test
    void testUpdateUserEmail() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        assertTrue(userService.updateUserEmail("test@example.com", "new@example.com"));
        User user = userService.login("new@example.com", "password");
        assertNotNull(user);
        assertEquals("new@example.com", user.getEmail());
        assertFalse(userService.updateUserEmail("unknown@example.com", "new@example.com"));
    }

    @Test
    void testToggleUserBlock() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        assertTrue(userService.toggleUserBlock("test@example.com"));
        User user = userService.login("test@example.com", "password");
        assertTrue(user.isAdmin());
        assertTrue(userService.toggleUserBlock("test@example.com"));
        user = userService.login("test@example.com", "password");
        assertFalse(user.isAdmin());
        assertFalse(userService.toggleUserBlock("unknown@example.com"));
    }

    @Test
    void testDeleteUser() {
        userService.registerUser("test@example.com", "password", "Test User", false);
        assertTrue(userService.deleteUser("test@example.com"));
        assertNull(userService.login("test@example.com", "password"));
        assertFalse(userService.deleteUser("unknown@example.com"));
    }

    @Test
    void testGetAllUsers() {
        userService.registerUser("user1@example.com", "password1", "User One", false);
        userService.registerUser("user2@example.com", "password2", "User Two", true);
        Map<String, User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.containsKey("user1@example.com"));
        assertTrue(users.containsKey("user2@example.com"));
    }
}
