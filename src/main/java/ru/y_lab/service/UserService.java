package ru.y_lab.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.y_lab.model.User;
import ru.y_lab.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для управления пользователями.
 * Предоставляет методы для регистрации, входа, обновления и удаления пользователей.
 */
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    /**
     * Регистрирует нового пользователя.
     *
     * @param email    Электронная почта пользователя.
     * @param password Пароль пользователя.
     * @param name     Имя пользователя.
     * @param isAdmin  Флаг, указывающий, является ли пользователь администратором.
     * @return {@code true}, если регистрация прошла успешно, иначе {@code false}.
     */
    public boolean registerUser(String email, String password, String name, boolean isAdmin) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            return false;
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setAdmin(isAdmin);
        userRepository.save(newUser);
        return true;
    }

    /**
     * Выполняет вход пользователя в систему.
     *
     * @param email    Электронная почта пользователя.
     * @param password Пароль пользователя.
     * @return Объект пользователя, если вход выполнен успешно, иначе {@code null}.
     */
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param email       Электронная почта пользователя.
     * @param newName     Новое имя пользователя.
     * @param newPassword Новый пароль пользователя.
     * @return {@code true}, если обновление прошло успешно, иначе {@code false}.
     */
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

    /**
     * Обновляет электронную почту пользователя.
     *
     * @param oldEmail Старая электронная почта пользователя.
     * @param newEmail Новая электронная почта пользователя.
     * @return {@code true}, если обновление прошло успешно, иначе {@code false}.
     */
    public boolean updateUserEmail(String oldEmail, String newEmail) {
        User user = userRepository.findByEmail(oldEmail);
        if (user != null) {
            user.setEmail(newEmail);
            userRepository.update(user);
            return true;
        }
        return false;
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Карта, где ключ — электронная почта пользователя, а значение — объект пользователя.
     */
    public Map<String, User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().collect(Collectors.toMap(User::getEmail, user -> user));
    }

    /**
     * Блокирует или разблокирует пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return {@code true}, если операция прошла успешно, иначе {@code false}.
     */
    public boolean toggleUserBlock(Long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setAdmin(!user.isAdmin());
            userRepository.update(user);
            return true;
        }
        return false;
    }

    /**
     * Удаляет пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return {@code true}, если удаление прошло успешно, иначе {@code false}.
     */
    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            userRepository.delete(user.getId());
            return true;
        }
        return false;
    }

    /**
     * Находит пользователя по email.
     *
     * @param email Электронная почта пользователя.
     * @return Объект пользователя, если найден, иначе {@code null}.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Находит пользователя по ID.
     *
     * @param userId Идентификатор пользователя.
     * @return Объект пользователя, если найден, иначе {@code null}.
     */
    public User findById(Long userId) {
        return userRepository.findById(userId);
    }
}

