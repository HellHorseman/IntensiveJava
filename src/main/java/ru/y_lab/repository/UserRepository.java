package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public UserRepository() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Сохраняет пользователя в базу данных.
     *
     * @param user Пользователь для сохранения.
     * @throws RuntimeException Если произошла ошибка при сохранении.
     */
    public void save(User user) {
        String sql = "INSERT INTO users (email, password, name, is_admin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setBoolean(4, user.isAdmin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении пользователя", e);
        }
    }

    /**
     * Возвращает пользователя по email.
     *
     * @param email Email пользователя.
     * @return Optional с пользователем, если найден.
     * @throws RuntimeException Если произошла ошибка при поиске.
     */
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователя по email", e);
        }
        return Optional.empty();
    }

    /**
     * Возвращает пользователя по ID.
     *
     * @param userId ID пользователя.
     * @return Optional с пользователем, если найден.
     * @throws RuntimeException Если произошла ошибка при поиске.
     */
    public Optional<User> findById(Long userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователя по ID", e);
        }
        return Optional.empty();
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список всех пользователей.
     * @throws RuntimeException Если произошла ошибка при поиске.
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех пользователей", e);
        }
        return users;
    }

    /**
     * Обновляет пользователя в базе данных.
     *
     * @param user Пользователь для обновления.
     * @throws RuntimeException Если произошла ошибка при обновлении.
     */
    public void update(User user) {
        String sql = "UPDATE users SET email = ?, password = ?, name = ?, is_admin = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setBoolean(4, user.isAdmin());
            stmt.setLong(5, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении пользователя", e);
        }
    }

    /**
     * Удаляет пользователя из базы данных по ID.
     *
     * @param userId ID пользователя.
     * @throws RuntimeException Если произошла ошибка при удалении.
     */
    public void delete(Long userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя", e);
        }
    }

    /**
     * Преобразует ResultSet в объект User.
     *
     * @param rs Результат запроса.
     * @return Объект User.
     * @throws SQLException Если произошла ошибка при чтении данных.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setAdmin(rs.getBoolean("is_admin"));
        return user;
    }
}