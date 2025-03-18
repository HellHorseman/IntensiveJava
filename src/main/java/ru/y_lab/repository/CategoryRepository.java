package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private final Connection connection;

    public CategoryRepository(Connection connection) {
        this.connection = connection;
    }

    public CategoryRepository() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Сохраняет категорию в базу данных.
     *
     * @param category Категория для сохранения.
     * @throws RuntimeException Если произошла ошибка при сохранении.
     */
    public void save(Category category) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении категории", e);
        }
    }

    /**
     * Возвращает список всех категорий.
     *
     * @return Список всех категорий.
     * @throws RuntimeException Если произошла ошибка при выполнении запроса.
     */
    public List<Category> findAll() {
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске всех категорий", e);
        }
        return categories;
    }

    /**
     * Обновляет категорию в базе данных.
     *
     * @param category Категория для обновления.
     * @throws RuntimeException Если произошла ошибка при обновлении.
     */
    public void update(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.setLong(2, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении категории", e);
        }
    }

    /**
     * Удаляет категорию из базы данных по ID.
     *
     * @param id ID категории.
     * @throws RuntimeException Если произошла ошибка при удалении.
     */
    public void delete(Long id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении категории", e);
        }
    }
}