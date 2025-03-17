package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    public void save(Category category) {
        String sql = "INSERT INTO categories (name) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));

                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }

        return categories;
    }

    public void update(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getName());
            statement.setLong(2, category.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM categories WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }
}