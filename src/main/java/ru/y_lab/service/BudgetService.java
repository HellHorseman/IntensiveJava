package ru.y_lab.service;

import ru.y_lab.model.Budget;
import ru.y_lab.model.Transaction;

public class BudgetService {
    private Budget budget;

    public BudgetService(double monthlyBudget) {
        this.budget = new Budget(monthlyBudget);
    }

    public void addTransaction(Transaction transaction) {
        budget.addTransaction(transaction.getAmount());
    }

    public boolean isOverBudget() {
        return budget.isOverBudget();
    }

    public void setMonthlyBudget(double amount) {
        budget.setMonthlyBudget(amount);
    }

    public double getCurrentSpending() {
        return budget.getCurrentSpending();
    }
}


