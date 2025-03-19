package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.model.User;
import ru.y_lab.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        Optional<User> existingUser = this.findByEmail(email);
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
        Optional<User> user = this.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
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
        Optional<User> user = this.findByEmail(email);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setName(newName);
            updatedUser.setPassword(newPassword);
            userRepository.update(updatedUser);
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
        Optional<User> user = this.findByEmail(oldEmail);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setEmail(newEmail);
            userRepository.update(updatedUser);
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
        Optional<User> user = this.findById(userId);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setAdmin(!updatedUser.isAdmin());
            userRepository.update(updatedUser);
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
        Optional<User> user = this.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get().getId());
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
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Находит пользователя по ID.
     *
     * @param userId Идентификатор пользователя.
     * @return Объект пользователя, если найден, иначе {@code null}.
     */
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}

