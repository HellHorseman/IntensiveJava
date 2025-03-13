package ru.y_lab.service;

import ru.y_lab.model.Budget;

import java.util.HashMap;
import java.util.Map;

public class BudgetService {
    private Map<String, Budget> budgets;

    public BudgetService() {
        budgets = new HashMap<>();
    }

    public boolean setMonthlyBudget(String userId, double monthlyBudget) {
        budgets.put(userId, new Budget(userId, monthlyBudget));
        return true;
    }

    public Budget getBudget(String userId) {
        return budgets.get(userId);
    }

    public boolean updateMonthlyBudget(String userId, double monthlyBudget) {
        Budget budget = budgets.get(userId);
        if (budget != null) {
            budget.setMonthlyBudget(monthlyBudget);
            return true;
        }
        return false;
    }
}


