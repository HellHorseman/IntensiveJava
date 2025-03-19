package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DisplayName("Тесты для AnalyticsService с использованием Testcontainers")
class AnalyticsServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private AnalyticsService analyticsService;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        TransactionRepository transactionRepository = new TransactionRepository(connection);

        transactionService = new TransactionService(transactionRepository);
        analyticsService = new AnalyticsService(transactionService);

        transactionRepository.findAll().forEach(transaction -> transactionRepository.delete(transaction.getId()));
    }

    @Test
    @DisplayName("Расчет баланса: успешное выполнение")
    void calculateBalance_WhenTransactionsExist_ShouldReturnCorrectBalance() {
        Long userId = 1L;
        Long categoryId = 1L;
        transactionService.addTransaction(userId, new BigDecimal("100.0"), categoryId, LocalDate.parse("2023-10-01"), "Monthly salary", TransactionType.INCOME);
        transactionService.addTransaction(userId, new BigDecimal("50.0"), categoryId, LocalDate.parse("2023-10-02"), "Groceries", TransactionType.EXPENSE);

        BigDecimal balance = analyticsService.calculateBalance(userId);

        assertEquals(new BigDecimal("50.0"), balance);
    }

    @Test
    @DisplayName("Расчет суммарного дохода: успешное выполнение")
    void calculateTotalIncome_WhenTransactionsExist_ShouldReturnCorrectTotal() {
        Long userId = 1L;
        Long categoryId = 1L;
        transactionService.addTransaction(userId, new BigDecimal("100.0"), categoryId, LocalDate.parse("2023-10-01"), "Monthly salary", TransactionType.INCOME);
        transactionService.addTransaction(userId, new BigDecimal("200.0"), categoryId, LocalDate.parse("2023-10-05"), "Annual bonus", TransactionType.INCOME);

        BigDecimal totalIncome = analyticsService.calculateTotalIncome(userId, LocalDate.parse("2023-10-01"), LocalDate.parse("2023-10-31"));

        assertEquals(new BigDecimal("300.0"), totalIncome);
    }

    @Test
    @DisplayName("Расчет суммарного расхода: успешное выполнение")
    void calculateTotalExpense_WhenTransactionsExist_ShouldReturnCorrectTotal() {
        Long userId = 1L;
        Long categoryId = 1L;
        transactionService.addTransaction(userId, new BigDecimal("50.0"), categoryId, LocalDate.parse("2023-10-02"), "Groceries", TransactionType.EXPENSE);
        transactionService.addTransaction(userId, new BigDecimal("30.0"), categoryId, LocalDate.parse("2023-10-03"), "Bus fare", TransactionType.EXPENSE);

        BigDecimal totalExpense = analyticsService.calculateTotalExpense(userId, LocalDate.parse("2023-10-01"), LocalDate.parse("2023-10-31"));

        assertEquals(new BigDecimal("80.0"), totalExpense);
    }
}