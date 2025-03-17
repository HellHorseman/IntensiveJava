package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.Transaction;
import ru.y_lab.enums.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    public void save(Transaction transaction) {
        String sql = "INSERT INTO finance.transactions (user_id, amount, category_id, date, description, type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, transaction.getUserId());
            statement.setBigDecimal(2, transaction.getAmount());
            statement.setLong(3, transaction.getCategoryId());
            statement.setDate(4, Date.valueOf(transaction.getDate()));
            statement.setString(5, transaction.getDescription());
            statement.setString(6, transaction.getType().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public List<Transaction> findByUserId(Long userId) {
        String sql = "SELECT * FROM finance.transactions WHERE user_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getLong("id"));
                transaction.setUserId(resultSet.getLong("user_id"));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setCategoryId(resultSet.getLong("category_id"));
                transaction.setDate(resultSet.getDate("date").toLocalDate());
                transaction.setDescription(resultSet.getString("description"));
                transaction.setType(TransactionType.valueOf(resultSet.getString("type")));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }

        return transactions;
    }

    public void update(Transaction transaction) {
        String sql = "UPDATE finance.transactions SET amount = ?, category_id = ?, date = ?, description = ?, type = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, transaction.getAmount());
            statement.setLong(2, transaction.getCategoryId());
            statement.setDate(3, Date.valueOf(transaction.getDate()));
            statement.setString(4, transaction.getDescription());
            statement.setString(5, transaction.getType().name());
            statement.setLong(6, transaction.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM finance.transactions WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL " + e.getMessage());
        }
    }
}