package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testRegisterUser() {
        assertTrue(userService.registerUser("test@example.com", "password", "Test User"));
        assertFalse(userService.registerUser("test@example.com", "password", "Test User"));
    }

    @Test
    void testLoginUser() {
        userService.registerUser("test@example.com", "password", "Test User");
        assertNotNull(userService.login("test@example.com", "password"));
        assertNull(userService.login("test@example.com", "wrongpassword"));
    }
}
