package ru.y_lab.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.model.Transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisplayName("Тесты для TransactionRepository")
public class TransactionRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private TransactionRepository transactionRepository;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        transactionRepository = new TransactionRepository(connection);
    }

    @Test
    @DisplayName("Сохранение транзакции")
    void testSave() {
        Transaction transaction = new Transaction();
        transaction.setUserId(1L);
        transaction.setAmount(BigDecimal.valueOf(100.50));
        transaction.setCategoryId(2L);
        transaction.setDate(LocalDate.now());
        transaction.setDescription("Покупка продуктов");
        transaction.setType(TransactionType.EXPENSE);

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findByUserId(1L);
        assertFalse(transactions.isEmpty());
        assertEquals("Покупка продуктов", transactions.get(0).getDescription());
    }

    @Test
    @DisplayName("Поиск транзакций по ID пользователя")
    void testFindByUserId() {
        Transaction transaction1 = new Transaction();
        transaction1.setUserId(1L);
        transaction1.setAmount(BigDecimal.valueOf(100.50));
        transaction1.setCategoryId(2L);
        transaction1.setDate(LocalDate.now());
        transaction1.setDescription("Обед");
        transaction1.setType(TransactionType.EXPENSE);

        Transaction transaction2 = new Transaction();
        transaction2.setUserId(1L);
        transaction2.setAmount(BigDecimal.valueOf(200.00));
        transaction2.setCategoryId(3L);
        transaction2.setDate(LocalDate.now());
        transaction2.setDescription("Ужин");
        transaction2.setType(TransactionType.EXPENSE);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);

        List<Transaction> transactions = transactionRepository.findByUserId(1L);
        assertEquals(2, transactions.size());
    }

    @Test
    @DisplayName("Обновление транзакции")
    void testUpdate() {
        Transaction transaction = new Transaction();
        transaction.setUserId(1L);
        transaction.setAmount(BigDecimal.valueOf(100.50));
        transaction.setCategoryId(2L);
        transaction.setDate(LocalDate.now());
        transaction.setDescription("Обед");
        transaction.setType(TransactionType.EXPENSE);

        transactionRepository.save(transaction);

        transaction.setAmount(BigDecimal.valueOf(150.00));
        transaction.setDescription("Перекус");
        transactionRepository.update(transaction);

        List<Transaction> transactions = transactionRepository.findByUserId(1L);
        assertEquals(BigDecimal.valueOf(150.00), transactions.get(0).getAmount());
        assertEquals("Перекус", transactions.get(0).getDescription());
    }

    @Test
    @DisplayName("Удаление транзакции")
    void testDelete() {
        Transaction transaction = new Transaction();
        transaction.setUserId(1L);
        transaction.setAmount(BigDecimal.valueOf(100.50));
        transaction.setCategoryId(2L);
        transaction.setDate(LocalDate.now());
        transaction.setDescription("Обед");
        transaction.setType(TransactionType.EXPENSE);

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findByUserId(1L);
        assertFalse(transactions.isEmpty());

        transactionRepository.delete(transactions.get(0).getId());
        assertTrue(transactionRepository.findByUserId(1L).isEmpty());
    }
}
