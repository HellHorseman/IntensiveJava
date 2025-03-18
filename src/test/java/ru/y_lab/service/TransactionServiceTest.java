package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.model.Transaction;
import ru.y_lab.repository.CategoryRepository;
import ru.y_lab.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisplayName("Тесты для TransactionService")
public class TransactionServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private TransactionService transactionService;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        transactionRepository = new TransactionRepository(connection);

        transactionService = new TransactionService(transactionRepository);

        transactionRepository.findAll().forEach(transaction -> transactionRepository.delete(transaction.getId()));
    }

    @Test
    @DisplayName("Добавление транзакции")
    void testAddTransaction() {
        assertTrue(transactionService.addTransaction(1L, BigDecimal.valueOf(100.0), 2L, LocalDate.parse("2023-10-01"), "Завтрак", TransactionType.EXPENSE));
        List<Transaction> transactions = transactionService.getTransactions(1L);
        assertFalse(transactions.isEmpty());
        assertEquals("Завтрак", transactions.get(0).getDescription());
    }

    @Test
    @DisplayName("Получение транзакций по ID пользователя")
    void testGetTransactions() {
        transactionService.addTransaction(1L, BigDecimal.valueOf(100.0), 2L, LocalDate.parse("2023-10-01"), "Завтрак", TransactionType.EXPENSE);
        transactionService.addTransaction(1L, BigDecimal.valueOf(200.0), 3L, LocalDate.parse("2023-10-02"), "Обед", TransactionType.EXPENSE);

        List<Transaction> transactions = transactionService.getTransactions(1L);
        assertEquals(2, transactions.size());
    }

    @Test
    @DisplayName("Редактирование транзакции")
    void testEditTransaction() {
        transactionService.addTransaction(1L, BigDecimal.valueOf(100.0), 2L, LocalDate.parse("2023-10-01"), "Lunch", TransactionType.EXPENSE);
        List<Transaction> transactions = transactionService.getTransactions(1L);
        Transaction transaction = transactions.get(0);

        assertTrue(transactionService.editTransaction(transaction.getId(), BigDecimal.valueOf(150.0), 3L, "Обед"));
        Transaction updatedTransaction = transactionService.getTransactions(1L).get(0);
        assertEquals(BigDecimal.valueOf(150.0), updatedTransaction.getAmount());
        assertEquals(3L, updatedTransaction.getCategoryId());
        assertEquals("Обед", updatedTransaction.getDescription());
    }

    @Test
    @DisplayName("Удаление транзакции")
    void testDeleteTransaction() {
        transactionService.addTransaction(1L, BigDecimal.valueOf(100.0), 2L, LocalDate.parse("2023-10-01"), "Lunch", TransactionType.EXPENSE);
        List<Transaction> transactions = transactionService.getTransactions(1L);
        Transaction transaction = transactions.get(0);

        assertTrue(transactionService.deleteTransaction(transaction.getId()));
        List<Transaction> remainingTransactions = transactionService.getTransactions(1L);
        assertTrue(remainingTransactions.isEmpty());
    }
}
