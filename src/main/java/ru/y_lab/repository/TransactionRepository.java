package ru.y_lab.repository;

import ru.y_lab.config.DatabaseConnection;
import ru.y_lab.model.Transaction;
import ru.y_lab.enums.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private final Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    public TransactionRepository() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Сохраняет транзакцию в базу данных.
     *
     * @param transaction Транзакция для сохранения.
     * @throws RuntimeException Если произошла ошибка при сохранении.
     */
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, amount, category_id, date, description, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, transaction.getUserId());
            stmt.setBigDecimal(2, transaction.getAmount());
            stmt.setLong(3, transaction.getCategoryId());
            stmt.setDate(4, Date.valueOf(transaction.getDate()));
            stmt.setString(5, transaction.getDescription());
            stmt.setString(6, transaction.getType().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении транзакции", e);
        }
    }

    /**
     * Возвращает список транзакций по ID пользователя.
     *
     * @param userId ID пользователя.
     * @return Список транзакций.
     * @throws RuntimeException Если произошла ошибка при поиске.
     */
    public List<Transaction> findByUserId(Long userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске транзакций по ID пользователя", e);
        }
        return transactions;
    }

    /**
     * Возвращает список всех транзакций.
     *
     * @return Список всех транзакций.
     * @throws RuntimeException Если произошла ошибка при поиске.
     */
    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transactions";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех транзакций", e);
        }
        return transactions;
    }

    /**
     * Обновляет транзакцию в базе данных.
     *
     * @param transaction Транзакция для обновления.
     * @throws RuntimeException Если произошла ошибка при обновлении.
     */
    public void update(Transaction transaction) {
        String sql = "UPDATE transactions SET amount = ?, category_id = ?, date = ?, description = ?, type = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, transaction.getAmount());
            stmt.setLong(2, transaction.getCategoryId());
            stmt.setDate(3, Date.valueOf(transaction.getDate()));
            stmt.setString(4, transaction.getDescription());
            stmt.setString(5, transaction.getType().name());
            stmt.setLong(6, transaction.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении транзакции", e);
        }
    }

    /**
     * Удаляет транзакцию из базы данных по ID.
     *
     * @param id ID транзакции.
     * @throws RuntimeException Если произошла ошибка при удалении.
     */
    public void delete(Long id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении транзакции", e);
        }
    }

    /**
     * Преобразует ResultSet в объект Transaction.
     *
     * @param rs Результат запроса.
     * @return Объект Transaction.
     * @throws SQLException Если произошла ошибка при чтении данных.
     */
    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong("id"));
        transaction.setUserId(rs.getLong("user_id"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setCategoryId(rs.getLong("category_id"));
        transaction.setDate(rs.getDate("date").toLocalDate());
        transaction.setDescription(rs.getString("description"));
        transaction.setType(TransactionType.valueOf(rs.getString("type")));
        return transaction;
    }
}