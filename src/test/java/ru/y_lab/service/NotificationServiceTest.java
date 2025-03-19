package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.model.Budget;
import ru.y_lab.model.Goal;
import ru.y_lab.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@Testcontainers
@DisplayName("Тесты для NotificationService с использованием Testcontainers")
class NotificationServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Mock
    private BudgetService budgetService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private EmailService emailService;

    @Mock
    private GoalService goalService;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService(budgetService, transactionService, goalService, emailService);
    }

    @Test
    @DisplayName("Проверка превышения бюджета: бюджет превышен")
    void checkBudgetExceeded_WhenBudgetExceeded_ShouldSendEmail() {
        Long userId = 1L;
        String email = "user1@example.com";
        when(budgetService.getBudget(userId)).thenReturn(new Budget(null, userId, new BigDecimal("1000.0")));
        when(transactionService.getTransactions(userId)).thenReturn(Arrays.asList(
                new Transaction(1L, userId, new BigDecimal("600.0"), 1L, LocalDate.of(2023, 10, 1), "Продукты", TransactionType.EXPENSE),
                new Transaction(2L, userId, new BigDecimal("500.0"), 2L, LocalDate.of(2023, 10, 2), "Проезд", TransactionType.EXPENSE)
        ));

        notificationService.checkBudgetExceeded(userId, email);

        verify(emailService, times(1)).sendEmail(eq(email), eq("Превышение бюджета"), anyString());
    }

    @Test
    @DisplayName("Проверка превышения бюджета: бюджет не превышен")
    void checkBudgetExceeded_WhenBudgetNotExceeded_ShouldNotSendEmail() {
        Long userId = 1L;
        String email = "user1@example.com";
        when(budgetService.getBudget(userId)).thenReturn(new Budget(null, userId, new BigDecimal("1000.0")));
        when(transactionService.getTransactions(userId)).thenReturn(Arrays.asList(
                new Transaction(1L, userId, new BigDecimal("600.0"), 1L, LocalDate.of(2023, 10, 1), "Продукты", TransactionType.EXPENSE),
                new Transaction(2L, userId, new BigDecimal("500.0"), 2L, LocalDate.of(2023, 10, 2), "Проезд", TransactionType.EXPENSE)
        ));

        notificationService.checkBudgetExceeded(userId, email);

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Проверка превышения бюджета: бюджет не установлен")
    void checkBudgetExceeded_WhenBudgetNotSet_ShouldNotSendEmail() {
        Long userId = 1L;
        String email = "user1@example.com";
        when(budgetService.getBudget(userId)).thenReturn(null);

        notificationService.checkBudgetExceeded(userId, email);

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Проверка прогресса по целям: цели существуют")
    void checkGoalProgress_WhenGoalsExist_ShouldNotSendEmail() {
        Long userId = 1L;
        String email = "user1@example.com";
        when(goalService.getGoals(userId)).thenReturn(Arrays.asList(
                new Goal(1L, userId, "Car", new BigDecimal("20000.0"), new BigDecimal("5000.0")),
                new Goal(2L, userId, "Vacation", new BigDecimal("5000.0"), new BigDecimal("2500.0"))
        ));

        notificationService.checkGoalProgress(userId, email);

        verify(emailService, times(0)).sendEmail(anyString(), eq("Достигнут прогресс по цели"), anyString());
    }

    @Test
    @DisplayName("Проверка прогресса по целям: цели отсутствуют")
    void checkGoalProgress_WhenNoGoals_ShouldNotSendEmail() {
        Long userId = 1L;
        String email = "user1@example.com";
        when(goalService.getGoals(userId)).thenReturn(List.of());

        notificationService.checkGoalProgress(userId, email);

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }
}