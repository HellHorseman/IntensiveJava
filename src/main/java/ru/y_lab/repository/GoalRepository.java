package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.Goal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalRepository {
    private final Connection connection;

    public GoalRepository(Connection connection) {
        this.connection = connection;
    }

    public GoalRepository() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Сохраняет цель в базу данных.
     *
     * @param goal Цель для сохранения.
     * @throws RuntimeException Если произошла ошибка при сохранении.
     */
    public void save(Goal goal) {
        String sql = "INSERT INTO goals (user_id, name, target_amount, current_amount) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, goal.getUserId());
            statement.setString(2, goal.getName());
            statement.setBigDecimal(3, goal.getTargetAmount());
            statement.setBigDecimal(4, goal.getCurrentAmount());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении цели", e);
        }
    }

    /**
     * Возвращает список целей по ID пользователя.
     *
     * @param userId ID пользователя.
     * @return Список целей.
     * @throws RuntimeException Если произошла ошибка при выполнении запроса.
     */
    public List<Goal> findByUserId(Long userId) {
        String sql = "SELECT * FROM goals WHERE user_id = ?";
        List<Goal> goals = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    goals.add(mapResultSetToGoal(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске целей по ID пользователя", e);
        }

        return goals;
    }

    /**
     * Возвращает список всех целей.
     *
     * @return Список всех целей.
     * @throws RuntimeException Если произошла ошибка при выполнении запроса.
     */
    public List<Goal> findAll() {
        String sql = "SELECT * FROM goals";
        List<Goal> goals = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                goals.add(mapResultSetToGoal(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех целей", e);
        }

        return goals;
    }

    /**
     * Обновляет цель в базе данных.
     *
     * @param goal Цель для обновления.
     * @throws RuntimeException Если произошла ошибка при обновлении.
     */
    public void update(Goal goal) {
        String sql = "UPDATE goals SET name = ?, target_amount = ?, current_amount = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, goal.getName());
            statement.setBigDecimal(2, goal.getTargetAmount());
            statement.setBigDecimal(3, goal.getCurrentAmount());
            statement.setLong(4, goal.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении цели", e);
        }
    }

    /**
     * Удаляет цель из базы данных по ID.
     *
     * @param id ID цели.
     * @throws RuntimeException Если произошла ошибка при удалении.
     */
    public void delete(Long id) {
        String sql = "DELETE FROM goals WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении цели", e);
        }
    }

    /**
     * Преобразует ResultSet в объект Goal.
     *
     * @param resultSet Результат запроса.
     * @return Объект Goal.
     * @throws SQLException Если произошла ошибка при чтении данных.
     */
    private Goal mapResultSetToGoal(ResultSet resultSet) throws SQLException {
        Goal goal = new Goal();
        goal.setId(resultSet.getLong("id"));
        goal.setUserId(resultSet.getLong("user_id"));
        goal.setName(resultSet.getString("name"));
        goal.setTargetAmount(resultSet.getBigDecimal("target_amount"));
        goal.setCurrentAmount(resultSet.getBigDecimal("current_amount"));
        return goal;
    }
}

