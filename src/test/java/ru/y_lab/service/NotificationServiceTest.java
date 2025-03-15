package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.y_lab.model.Budget;
import ru.y_lab.model.Goal;
import ru.y_lab.model.Transaction;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class NotificationServiceTest {
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
        notificationService = new NotificationService(budgetService, transactionService, emailService);
    }

    @Test
    void testCheckBudgetExceeded_WhenBudgetExceeded() {
        when(budgetService.getBudget("user1@example.com")).thenReturn(new Budget("user1@example.com", 1000.0));
        when(transactionService.getTransactions("user1@example.com")).thenReturn(Arrays.asList(
                new Transaction("1", "user1@example.com", 600.0, "Food", "2023-10-01", "Groceries", "expense"),
                new Transaction("2", "user1@example.com", 500.0, "Transport", "2023-10-02", "Bus fare", "expense")
        ));

        notificationService.checkBudgetExceeded("user1@example.com", "user1@example.com");

        verify(emailService, times(1)).sendEmail(eq("user1@example.com"), eq("Превышение бюджета"), anyString());
    }

    @Test
    void testCheckBudgetExceeded_WhenBudgetNotExceeded() {
        // Настройка моков
        when(budgetService.getBudget("user1@example.com")).thenReturn(new Budget("user1@example.com", 1000.0));
        when(transactionService.getTransactions("user1@example.com")).thenReturn(Arrays.asList(
                new Transaction("1", "user1@example.com", 400.0, "Food", "2023-10-01", "Groceries", "expense"),
                new Transaction("2", "user1@example.com", 300.0, "Transport", "2023-10-02", "Bus fare", "expense")
        ));

        notificationService.checkBudgetExceeded("user1@example.com", "user1@example.com");

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testCheckBudgetExceeded_WhenBudgetNotSet() {
        when(budgetService.getBudget("user1@example.com")).thenReturn(null);

        notificationService.checkBudgetExceeded("user1@example.com", "user1@example.com");

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testCheckGoalProgress_WhenGoalsExist() {
        // Настройка моков
        when(goalService.getGoals("user1@example.com")).thenReturn(Arrays.asList(
                new Goal("1", "user1@example.com", "Car", 20000.0, 5000.0),
                new Goal("2", "user1@example.com", "Vacation", 5000.0, 2500.0)
        ));

        notificationService.checkGoalProgress("user1@example.com", "user1@example.com", goalService);

        verify(emailService, times(0)).sendEmail(anyString(), eq("Достигнут прогресс по цели"), anyString());
    }

    @Test
    void testCheckGoalProgress_WhenNoGoals() {
        when(goalService.getGoals("user1@example.com")).thenReturn(List.of());

        notificationService.checkGoalProgress("user1@example.com", "user1@example.com", goalService);

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }
}
