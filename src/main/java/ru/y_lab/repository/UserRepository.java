package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public void save(User user) {
        String sql = "INSERT INTO finance.users (email, password, name, is_admin) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setBoolean(4, user.isAdmin());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM finance.users WHERE email = ?";
        User user = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }

        return user;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM finance.users";
        List<User> users = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setAdmin(resultSet.getBoolean("is_admin"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }

        return users;
    }

    public void update(User user) {
        String sql = "UPDATE finance.users SET email = ?, password = ?, name = ?, is_admin = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setBoolean(4, user.isAdmin());
            statement.setLong(5, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM finance.users WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }
}