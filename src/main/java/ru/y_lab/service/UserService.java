package ru.y_lab.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.y_lab.model.User;
import ru.y_lab.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    // Регистрация пользователя с указанием роли
    public boolean registerUser(String email, String password, String name, boolean isAdmin) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return false; // Пользователь с таким email уже существует
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setAdmin(isAdmin);
        userRepository.save(newUser);
        return true;
    }

    // Вход в систему
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Обновление данных пользователя
    public boolean updateUser(String email, String newName, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setName(newName);
            user.setPassword(newPassword);
            userRepository.update(user);
            return true;
        }
        return false;
    }

    // Обновление email пользователя
    public boolean updateUserEmail(String oldEmail, String newEmail) {
        User user = userRepository.findByEmail(oldEmail);
        if (user != null) {
            user.setEmail(newEmail);
            userRepository.update(user);
            return true;
        }
        return false;
    }

    // Получение списка всех пользователей
    public Map<String, User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().collect(Collectors.toMap(User::getEmail, user -> user));
    }

    // Блокировка/разблокировка пользователя
    public boolean toggleUserBlock(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setAdmin(!user.isAdmin()); // Пример блокировки через изменение роли
            userRepository.update(user);
            return true;
        }
        return false;
    }

    // Удаление пользователя
    public boolean deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user.getId());
            return true;
        }
        return false;
    }
}

