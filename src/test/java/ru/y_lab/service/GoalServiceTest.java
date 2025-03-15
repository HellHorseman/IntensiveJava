package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoalServiceTest {
    private GoalService goalService;

    @BeforeEach
    void setUp() {
        goalService = new GoalService();
    }

    @Test
    void testAddGoal() {
        assertTrue(goalService.addGoal("user1@example.com", "Новая машина", 20000.0));
    }

    @Test
    void testGetGoals() {
        goalService.addGoal("user1@example.com", "Новая машина", 20000.0);
        assertEquals(1, goalService.getGoals("user1@example.com").size());
    }

    @Test
    void testUpdateGoal() {
        goalService.addGoal("user1@example.com", "Новая машина", 20000.0);
        String goalId = goalService.getGoals("user1@example.com").get(0).getId();
        assertTrue(goalService.updateGoal(goalId, "Новая машина", 25000.0, 5000.0));
    }

    @Test
    void testDeleteGoal() {
        goalService.addGoal("user1@example.com", "Новая машина", 20000.0);
        String goalId = goalService.getGoals("user1@example.com").get(0).getId();
        assertTrue(goalService.deleteGoal(goalId));
    }
}
