package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.Budget;

import java.sql.*;

public class BudgetRepository {
    public void save(Budget budget) {
        String sql = "INSERT INTO finance.budgets (user_id, monthly_budget) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, budget.getUserId());
            statement.setBigDecimal(2, budget.getMonthlyBudget());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public Budget findByUserId(Long userId) {
        String sql = "SELECT * FROM finance.budgets WHERE user_id = ?";
        Budget budget = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                budget = new Budget();
                budget.setId(resultSet.getLong("id"));
                budget.setUserId(resultSet.getLong("user_id"));
                budget.setMonthlyBudget(resultSet.getBigDecimal("monthly_budget"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }

        return budget;
    }

    public void update(Budget budget) {
        String sql = "UPDATE finance.budgets SET monthly_budget = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, budget.getMonthlyBudget());
            statement.setLong(2, budget.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM finance.budgets WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }
}