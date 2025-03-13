package ru.y_lab.service;

import ru.y_lab.model.Budget;
import ru.y_lab.model.Goal;
import ru.y_lab.model.Transaction;

import java.util.List;

public class NotificationService {
    private BudgetService budgetService;
    private TransactionService transactionService;

    public NotificationService(BudgetService budgetService, TransactionService transactionService) {
        this.budgetService = budgetService;
        this.transactionService = transactionService;
    }

    // Проверка превышения бюджета
    public void checkBudgetExceeded(String userId) {
        Budget budget = budgetService.getBudget(userId);
        if (budget == null) {
            System.out.println("Бюджет не установлен");
            return;
        }

        double totalExpenses = transactionService.getTransactions(userId).stream()
                .filter(t -> t.getType().equals("expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        if (totalExpenses > budget.getMonthlyBudget()) {
            System.out.println("Внимание! Вы превысили месячный бюджет.");
        } else {
            System.out.println("Вы в пределах бюджета.");
        }
    }

    // Уведомление о прогрессе по целям
    public void checkGoalProgress(String userId, GoalService goalService) {
        List<Goal> goals = goalService.getGoals(userId);
        for (Goal goal : goals) {
            double progress = (goal.getCurrentAmount() / goal.getTargetAmount()) * 100;
            System.out.println("Цель: " + goal.getName() + ", Прогресс: " + progress + "%");
        }
    }
}
