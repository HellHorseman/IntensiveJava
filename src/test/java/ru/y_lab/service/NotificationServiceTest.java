package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationServiceTest {
    private NotificationService notificationService;
    private BudgetService budgetService;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetService();
        transactionService = new TransactionService();
        notificationService = new NotificationService(budgetService, transactionService);
    }

    @Test
    void testCheckBudgetExceeded() {
        budgetService.setMonthlyBudget("user1@example.com", 100.0);
        transactionService.addTransaction("user1@example.com", 60.0, "Food", "2023-10-02", "Groceries", "expense");
        transactionService.addTransaction("user1@example.com", 50.0, "Transport", "2023-10-03", "Bus fare", "expense");
        notificationService.checkBudgetExceeded("user1@example.com");
    }
}
