package ru.y_lab.service;

import ru.y_lab.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private int idCounter = 1;

    // Регистрация нового пользователя
    public boolean register(String name, String email, String password) {
        if (emailExists(email)) {
            System.out.println("Ошибка: пользователь с таким email уже существует.");
            return false;
        }
        int id = idCounter++;
        users.add(new User(id, name, email, password));
        System.out.println("Регистрация успешна!");
        return true;
    }

    // Вход в систему
    public Optional<User> login(String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
    }

    // Редактирование профиля пользователя
    public boolean updateUser(int userId, String newName, String newEmail, String newPassword) {
        Optional<User> userOpt = findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.getEmail().equals(newEmail) && emailExists(newEmail)) {
                System.out.println("Ошибка: этот email уже используется другим пользователем.");
                return false;
            }
            user.setName(newName);
            user.setEmail(newEmail);
            user.setPassword(newPassword);
            System.out.println("Данные пользователя обновлены.");
            return true;
        }
        System.out.println("Ошибка: пользователь не найден.");
        return false;
    }

    // Удаление пользователя
    public boolean deleteUser(int userId) {
        return users.removeIf(user -> user.getId() == userId);
    }

    // Получение всех пользователей (для администратора)
    public List<User> getAllUsers() {
        return users;
    }

    // Поиск пользователя по ID
    private Optional<User> findById(int userId) {
        return users.stream().filter(user -> user.getId() == userId).findFirst();
    }

    // Проверка, существует ли email
    private boolean emailExists(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }
}

