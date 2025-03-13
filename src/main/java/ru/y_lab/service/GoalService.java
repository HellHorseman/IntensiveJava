package ru.y_lab.service;

import ru.y_lab.model.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GoalService {
    private List<Goal> goals;

    public GoalService() {
        goals = new ArrayList<>();
    }

    public boolean addGoal(String userId, String name, double targetAmount) {
        String id = UUID.randomUUID().toString();
        goals.add(new Goal(id, userId, name, targetAmount, 0.0));
        return true;
    }

    public List<Goal> getGoals(String userId) {
        List<Goal> userGoals = new ArrayList<>();
        for (Goal goal : goals) {
            if (goal.getUserId().equals(userId)) {
                userGoals.add(goal);
            }
        }
        return userGoals;
    }

    public boolean updateGoal(String goalId, String name, double targetAmount, double currentAmount) {
        for (Goal goal : goals) {
            if (goal.getId().equals(goalId)) {
                goal.setName(name);
                goal.setTargetAmount(targetAmount);
                goal.setCurrentAmount(currentAmount);
                return true;
            }
        }
        return false;
    }

    public boolean deleteGoal(String goalId) {
        for (Goal goal : goals) {
            if (goal.getId().equals(goalId)) {
                goals.remove(goal);
                return true;
            }
        }
        return false;
    }
}
