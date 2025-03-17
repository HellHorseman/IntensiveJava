package ru.y_lab.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.model.Goal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisplayName("Тесты для GoalRepository")
public class GoalRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private GoalRepository goalRepository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        goalRepository = new GoalRepository();
    }

    @Test
    @DisplayName("Сохранение цели")
    void testSave() {
        Goal goal = new Goal();
        goal.setUserId(1L);
        goal.setName("Накопить на отпуск");
        goal.setTargetAmount(BigDecimal.valueOf(5000.00));
        goal.setCurrentAmount(BigDecimal.valueOf(1000.00));

        goalRepository.save(goal);

        List<Goal> goals = goalRepository.findByUserId(1L);
        assertFalse(goals.isEmpty());
        assertEquals("Накопить на отпуск", goals.get(0).getName());
    }

    @Test
    @DisplayName("Поиск целей по ID пользователя")
    void testFindByUserId() {
        Goal goal1 = new Goal();
        goal1.setUserId(1L);
        goal1.setName("Накопить на отпуск");
        goal1.setTargetAmount(BigDecimal.valueOf(5000.00));
        goal1.setCurrentAmount(BigDecimal.valueOf(1000.00));

        Goal goal2 = new Goal();
        goal2.setUserId(1L);
        goal2.setName("Купить машину");
        goal2.setTargetAmount(BigDecimal.valueOf(20000.00));
        goal2.setCurrentAmount(BigDecimal.valueOf(5000.00));

        goalRepository.save(goal1);
        goalRepository.save(goal2);

        List<Goal> goals = goalRepository.findByUserId(1L);
        assertEquals(2, goals.size());
    }

    @Test
    @DisplayName("Обновление цели")
    void testUpdate() {
        Goal goal = new Goal();
        goal.setUserId(1L);
        goal.setName("Накопить на отпуск");
        goal.setTargetAmount(BigDecimal.valueOf(5000.00));
        goal.setCurrentAmount(BigDecimal.valueOf(1000.00));

        goalRepository.save(goal);

        List<Goal> goals = goalRepository.findByUserId(1L);
        Goal savedGoal = goals.get(0);

        savedGoal.setName("Накопить на дом");
        savedGoal.setTargetAmount(BigDecimal.valueOf(100000.00));
        goalRepository.update(savedGoal);

        List<Goal> updatedGoals = goalRepository.findByUserId(1L);
        assertEquals("Накопить на дом", updatedGoals.get(0).getName());
        assertEquals(BigDecimal.valueOf(100000.00), updatedGoals.get(0).getTargetAmount());
    }

    @Test
    @DisplayName("Удаление цели")
    void testDelete() {
        Goal goal = new Goal();
        goal.setUserId(1L);
        goal.setName("Накопить на отпуск");
        goal.setTargetAmount(BigDecimal.valueOf(5000.00));
        goal.setCurrentAmount(BigDecimal.valueOf(1000.00));

        goalRepository.save(goal);

        List<Goal> goals = goalRepository.findByUserId(1L);
        Goal savedGoal = goals.get(0);

        goalRepository.delete(savedGoal.getId());

        List<Goal> remainingGoals = goalRepository.findByUserId(1L);
        assertTrue(remainingGoals.isEmpty());
    }
}
