package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.model.Goal;
import ru.y_lab.repository.GoalRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DisplayName("Тесты для GoalService с использованием Testcontainers")
class GoalServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private GoalService goalService;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        GoalRepository goalRepository = new GoalRepository(connection);

        goalService = new GoalService(goalRepository);

        goalRepository.findAll().forEach(goal -> goalRepository.delete(goal.getId()));
    }

    @Test
    @DisplayName("Добавление новой цели: успешное добавление")
    void addGoal_WhenValidData_ShouldReturnTrue() {
        Long userId = 1L;
        String name = "Новая машина";
        BigDecimal targetAmount = new BigDecimal("20000.0");

        boolean result = goalService.addGoal(userId, name, targetAmount);

        assertTrue(result);

        List<Goal> goals = goalService.getGoals(userId);
        assertEquals(1, goals.size());

        Goal goal = goals.get(0);
        assertEquals(name, goal.getName());
        assertEquals(targetAmount, goal.getTargetAmount());
        assertEquals(BigDecimal.ZERO, goal.getCurrentAmount());
    }

    @Test
    @DisplayName("Получение списка целей: успешное получение")
    void getGoals_WhenGoalsExist_ShouldReturnListOfGoals() {
        Long userId = 1L;
        goalService.addGoal(userId, "Новая машина", new BigDecimal("20000.0"));
        goalService.addGoal(userId, "Отпуск", new BigDecimal("5000.0"));

        List<Goal> goals = goalService.getGoals(userId);

        assertEquals(2, goals.size());
    }

    @Test
    @DisplayName("Обновление цели: успешное обновление")
    void updateGoal_WhenValidData_ShouldReturnTrue() {
        Long userId = 1L;
        goalService.addGoal(userId, "Новая машина", new BigDecimal("20000.0"));
        Long goalId = goalService.getGoals(userId).get(0).getId();

        boolean result = goalService.updateGoal(goalId, "Новая машина", new BigDecimal("25000.0"), new BigDecimal("5000.0"));

        assertTrue(result);

        Goal updatedGoal = goalService.getGoals(userId).get(0);
        assertEquals(new BigDecimal("25000.0"), updatedGoal.getTargetAmount());
        assertEquals(new BigDecimal("5000.0"), updatedGoal.getCurrentAmount());
    }

    @Test
    @DisplayName("Удаление цели: успешное удаление")
    void deleteGoal_WhenGoalExists_ShouldReturnTrue() {
        Long userId = 1L;
        goalService.addGoal(userId, "Новая машина", new BigDecimal("20000.0"));
        Long goalId = goalService.getGoals(userId).get(0).getId();

        boolean result = goalService.deleteGoal(goalId);

        assertTrue(result);

        List<Goal> goals = goalService.getGoals(userId);
        assertEquals(0, goals.size());
    }
}
