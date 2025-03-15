package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyticsServiceTest {
    private AnalyticsService analyticsService;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
        analyticsService = new AnalyticsService(transactionService);
    }

    @Test
    void testCalculateBalance() {
        transactionService.addTransaction("user1@example.com", 100.0, "Salary", "2023-10-01", "Monthly salary", "income");
        transactionService.addTransaction("user1@example.com", 50.0, "Food", "2023-10-02", "Groceries", "expense");
        assertEquals(50.0, analyticsService.calculateBalance("user1@example.com"));
    }

    @Test
    void testCalculateTotalIncome() {
        transactionService.addTransaction("user1@example.com", 100.0, "Salary", "2023-10-01", "Monthly salary", "income");
        transactionService.addTransaction("user1@example.com", 200.0, "Bonus", "2023-10-05", "Annual bonus", "income");
        assertEquals(300.0, analyticsService.calculateTotalIncome("user1@example.com", "2023-10-01", "2023-10-31"));
    }

    @Test
    void testCalculateTotalExpense() {
        transactionService.addTransaction("user1@example.com", 50.0, "Food", "2023-10-02", "Groceries", "expense");
        transactionService.addTransaction("user1@example.com", 30.0, "Transport", "2023-10-03", "Bus fare", "expense");
        assertEquals(80.0, analyticsService.calculateTotalExpense("user1@example.com", "2023-10-01", "2023-10-31"));
    }
}
