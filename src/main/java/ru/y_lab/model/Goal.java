package ru.y_lab.model;

public class Goal {
    private double targetAmount;
    private double currentAmount;

    public Goal(double targetAmount) {
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addProgress(double amount) {
        this.currentAmount += amount;
    }

    public boolean isGoalAchieved() {
        return currentAmount >= targetAmount;
    }
}

