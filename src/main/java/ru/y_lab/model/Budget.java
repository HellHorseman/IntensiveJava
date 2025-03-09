package ru.y_lab.model;

public class Budget {
    private double monthlyBudget;
    private double currentSpending;

    public Budget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
        this.currentSpending = 0.0;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public void addTransaction(double amount) {
        this.currentSpending += amount;
    }

    public boolean isOverBudget() {
        return currentSpending > monthlyBudget;
    }
}

