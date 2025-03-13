package ru.y_lab.service;

import ru.y_lab.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> users;

    public UserService() {
        users = new HashMap<>();
    }

    // Регистрация пользователя с указанием роли
    public boolean registerUser(String email, String password, String name, boolean isAdmin) {
        if (users.containsKey(email)) {
            return false;
        }
        users.put(email, new User(email, password, name, isAdmin));
        return true;
    }

    // Вход в систему
    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Обновление данных пользователя
    public boolean updateUser(String email, String newName, String newPassword) {
        User user = users.get(email);
        if (user != null) {
            user.setName(newName);
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

    // Обновление email пользователя
    public boolean updateUserEmail(String oldEmail, String newEmail) {
        User user = users.get(oldEmail);
        if (user != null) {
            users.remove(oldEmail);
            user.setEmail(newEmail);
            users.put(newEmail, user);
            return true;
        }
        return false;
    }

    // Получение списка всех пользователей
    public Map<String, User> getAllUsers() {
        return users;
    }

    // Блокировка/разблокировка пользователя
    public boolean toggleUserBlock(String email) {
        User user = users.get(email);
        if (user != null) {
            user.setAdmin(!user.isAdmin()); // Пример блокировки через изменение роли
            return true;
        }
        return false;
    }

    // Удаление пользователя
    public boolean deleteUser(String email) {
        return users.remove(email) != null;
    }
}

