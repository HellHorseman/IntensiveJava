package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetServiceTest {
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetService();
    }

    @Test
    void testSetMonthlyBudget() {
        assertTrue(budgetService.setMonthlyBudget("user1@example.com", 1000.0));
    }

    @Test
    void testGetBudget() {
        budgetService.setMonthlyBudget("user1@example.com", 1000.0);
        assertNotNull(budgetService.getBudget("user1@example.com"));
    }

    @Test
    void testUpdateMonthlyBudget() {
        budgetService.setMonthlyBudget("user1@example.com", 1000.0);
        assertTrue(budgetService.updateMonthlyBudget("user1@example.com", 1500.0));
    }
}
