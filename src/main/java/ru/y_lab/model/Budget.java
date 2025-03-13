package ru.y_lab.model;

public class Budget {
    private String userId;
    private double monthlyBudget;

    public Budget(String userId, double monthlyBudget) {
        this.userId = userId;
        this.monthlyBudget = monthlyBudget;
    }

    public String getUserId() {
        return userId;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }
}

