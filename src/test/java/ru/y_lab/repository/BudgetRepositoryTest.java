package ru.y_lab.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.model.Budget;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisplayName("Тесты для BudgetRepository")
public class BudgetRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private BudgetRepository budgetRepository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        budgetRepository = new BudgetRepository();
    }

    @Test
    @DisplayName("Сохранение бюджета")
    void testSave() {
        Budget budget = new Budget();
        budget.setUserId(1L);
        budget.setMonthlyBudget(BigDecimal.valueOf(1000.00));

        budgetRepository.save(budget);

        Budget foundBudget = budgetRepository.findByUserId(1L);
        assertNotNull(foundBudget);
        assertEquals(BigDecimal.valueOf(1000.00), foundBudget.getMonthlyBudget());
    }

    @Test
    @DisplayName("Поиск бюджета по ID пользователя")
    void testFindByUserId() {
        Budget budget = new Budget();
        budget.setUserId(1L);
        budget.setMonthlyBudget(BigDecimal.valueOf(1000.00));

        budgetRepository.save(budget);

        Budget foundBudget = budgetRepository.findByUserId(1L);
        assertNotNull(foundBudget);
        assertEquals(1L, foundBudget.getUserId());
    }

    @Test
    @DisplayName("Обновление бюджета")
    void testUpdate() {
        Budget budget = new Budget();
        budget.setUserId(1L);
        budget.setMonthlyBudget(BigDecimal.valueOf(1000.00));

        budgetRepository.save(budget);

        Budget foundBudget = budgetRepository.findByUserId(1L);
        foundBudget.setMonthlyBudget(BigDecimal.valueOf(1500.00));
        budgetRepository.update(foundBudget);

        Budget updatedBudget = budgetRepository.findByUserId(1L);
        assertEquals(BigDecimal.valueOf(1500.00), updatedBudget.getMonthlyBudget());
    }

    @Test
    @DisplayName("Удаление бюджета")
    void testDelete() {
        Budget budget = new Budget();
        budget.setUserId(1L);
        budget.setMonthlyBudget(BigDecimal.valueOf(1000.00));

        budgetRepository.save(budget);

        Budget foundBudget = budgetRepository.findByUserId(1L);
        budgetRepository.delete(foundBudget.getId());

        Budget deletedBudget = budgetRepository.findByUserId(1L);
        assertNull(deletedBudget);
    }
}
