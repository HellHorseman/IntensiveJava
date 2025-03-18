package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.Budget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetRepository {
    private final Connection connection;

    public BudgetRepository(Connection connection) {
        this.connection = connection;
    }

    public BudgetRepository() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Сохраняет бюджет в базу данных.
     *
     * @param budget Бюджет для сохранения.
     * @throws RuntimeException Если произошла ошибка при сохранении.
     */
    public void save(Budget budget) {
        String sql = "INSERT INTO budgets (user_id, monthly_budget) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, budget.getUserId());
            statement.setBigDecimal(2, budget.getMonthlyBudget());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении бюджета", e);
        }
    }

    /**
     * Возвращает бюджет по ID пользователя.
     *
     * @param userId ID пользователя.
     * @return Бюджет.
     * @throws RuntimeException Если произошла ошибка при поиске.
     */
    public Budget findByUserId(Long userId) {
        String sql = "SELECT * FROM budgets WHERE user_id = ?";
        Budget budget = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                budget = new Budget();
                budget.setId(resultSet.getLong("id"));
                budget.setUserId(resultSet.getLong("user_id"));
                budget.setMonthlyBudget(resultSet.getBigDecimal("monthly_budget"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске бюджета по ID пользователя", e);
        }

        return budget;
    }

    /**
     * Возвращает список всех бюджетов.
     *
     * @return Список всех бюджетов.
     * @throws RuntimeException Если произошла ошибка при поиске.
     */
    public List<Budget> findAll() {
        String sql = "SELECT * FROM budgets";
        List<Budget> budgets = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Budget budget = new Budget();
                budget.setId(resultSet.getLong("id"));
                budget.setUserId(resultSet.getLong("user_id"));
                budget.setMonthlyBudget(resultSet.getBigDecimal("monthly_budget"));
                budgets.add(budget);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске бюджетов", e);
        }

        return budgets;
    }

    /**
     * Обновляет бюджет в базе данных.
     *
     * @param budget Бюджет для обновления.
     * @throws RuntimeException Если произошла ошибка при обновлении.
     */
    public void update(Budget budget) {
        String sql = "UPDATE budgets SET monthly_budget = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, budget.getMonthlyBudget());
            statement.setLong(2, budget.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении бюджета", e);
        }
    }

    /**
     * Удаляет бюджет из базы данных по ID.
     *
     * @param id ID бюджета.
     * @throws RuntimeException Если произошла ошибка при удалении.
     */
    public void delete(Long id) {
        String sql = "DELETE FROM budgets WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении бюджета", e);
        }
    }
}