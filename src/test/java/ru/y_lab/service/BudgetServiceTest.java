package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.model.Budget;
import ru.y_lab.repository.BudgetRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisplayName("Тесты для BudgetService с использованием Testcontainers")
class BudgetServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private BudgetService budgetService;
    private BudgetRepository budgetRepository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        budgetRepository = new BudgetRepository(connection);

        budgetService = new BudgetService(budgetRepository);

        budgetRepository.findAll().forEach(budget -> budgetRepository.delete(budget.getId()));
    }

    @Test
    @DisplayName("Установка месячного бюджета: успешное выполнение")
    void setMonthlyBudget_WhenValidData_ShouldReturnTrue() {
        Long userId = 1L;
        BigDecimal monthlyBudget = new BigDecimal("1000.0");

        boolean result = budgetService.setMonthlyBudget(userId, monthlyBudget);

        assertTrue(result);

        Budget budget = budgetService.getBudget(userId);
        assertNotNull(budget);
        assertEquals(monthlyBudget, budget.getMonthlyBudget());
    }

    @Test
    @DisplayName("Получение бюджета: успешное выполнение")
    void getBudget_WhenBudgetExists_ShouldReturnBudget() {
        Long userId = 1L;
        BigDecimal monthlyBudget = new BigDecimal("1000.0");
        budgetService.setMonthlyBudget(userId, monthlyBudget);

        Budget budget = budgetService.getBudget(userId);

        assertNotNull(budget);
        assertEquals(monthlyBudget, budget.getMonthlyBudget());
    }

    @Test
    @DisplayName("Обновление месячного бюджета: успешное выполнение")
    void updateMonthlyBudget_WhenValidData_ShouldReturnTrue() {
        Long userId = 1L;
        BigDecimal initialBudget = new BigDecimal("1000.0");
        BigDecimal updatedBudget = new BigDecimal("1500.0");
        budgetService.setMonthlyBudget(userId, initialBudget);

        boolean result = budgetService.updateMonthlyBudget(userId, updatedBudget);

        assertTrue(result);

        Budget budget = budgetService.getBudget(userId);
        assertNotNull(budget);
        assertEquals(updatedBudget, budget.getMonthlyBudget());
    }

    @Test
    @DisplayName("Удаление бюджета: успешное выполнение")
    void deleteBudget_WhenBudgetExists_ShouldReturnTrue() {
        Long userId = 1L;
        BigDecimal monthlyBudget = new BigDecimal("1000.0");
        budgetService.setMonthlyBudget(userId, monthlyBudget);

        boolean result = budgetService.deleteBudget(userId);

        assertTrue(result);

        Budget budget = budgetService.getBudget(userId);
        assertNull(budget);
    }
}
