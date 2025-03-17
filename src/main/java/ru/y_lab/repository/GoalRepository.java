package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.Goal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalRepository {
    public void save(Goal goal) {
        String sql = "INSERT INTO finance.goals (user_id, name, target_amount, current_amount) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, goal.getUserId());
            statement.setString(2, goal.getName());
            statement.setBigDecimal(3, goal.getTargetAmount());
            statement.setBigDecimal(4, goal.getCurrentAmount());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public List<Goal> findByUserId(Long userId) {
        String sql = "SELECT * FROM finance.goals WHERE user_id = ?";
        List<Goal> goals = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Goal goal = new Goal();
                goal.setId(resultSet.getLong("id"));
                goal.setUserId(resultSet.getLong("user_id"));
                goal.setName(resultSet.getString("name"));
                goal.setTargetAmount(resultSet.getBigDecimal("target_amount"));
                goal.setCurrentAmount(resultSet.getBigDecimal("current_amount"));

                goals.add(goal);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }

        return goals;
    }

    public void update(Goal goal) {
        String sql = "UPDATE finance.goals SET name = ?, target_amount = ?, current_amount = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, goal.getName());
            statement.setBigDecimal(2, goal.getTargetAmount());
            statement.setBigDecimal(3, goal.getCurrentAmount());
            statement.setLong(4, goal.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM finance.goals WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }
}
