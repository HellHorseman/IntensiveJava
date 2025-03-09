package ru.y_lab.service;

import ru.y_lab.model.Goal;

public class GoalService {
    private Goal goal;

    public GoalService(double targetAmount) {
        this.goal = new Goal(targetAmount);
    }

    public void addProgress(double amount) {
        goal.addProgress(amount);
    }

    public boolean isGoalAchieved() {
        return goal.isGoalAchieved();
    }

    public double getCurrentAmount() {
        return goal.getCurrentAmount();
    }
}

