package ru.y_lab.service;

import ru.y_lab.model.Budget;
import ru.y_lab.model.Goal;
import ru.y_lab.model.Transaction;

import java.util.List;

public class NotificationService {
    private BudgetService budgetService;
    private TransactionService transactionService;
    private EmailService emailService; // Добавляем EmailService

    public NotificationService(BudgetService budgetService, TransactionService transactionService, EmailService emailService) {
        this.budgetService = budgetService;
        this.transactionService = transactionService;
        this.emailService = emailService;
    }

    // Проверка превышения бюджета
    public void checkBudgetExceeded(String userId, String email) {
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
            String message = "Внимание! Вы превысили месячный бюджет. Текущие расходы: " + totalExpenses + ", Бюджет: " + budget.getMonthlyBudget();
            System.out.println(message);
            emailService.sendEmail(email, "Превышение бюджета", message); // Отправляем email
        } else {
            System.out.println("Вы в пределах бюджета.");
        }
    }

    // Уведомление о прогрессе по целям
    public void checkGoalProgress(String userId, String email, GoalService goalService) {
        List<Goal> goals = goalService.getGoals(userId);
        for (Goal goal : goals) {
            double progress = (goal.getCurrentAmount() / goal.getTargetAmount()) * 100;
            String message = "Цель: " + goal.getName() + ", Прогресс: " + progress + "%";
            System.out.println(message);
            if (progress >= 100) {
                emailService.sendEmail(email, "Достигнут прогресс по цели", message); // Отправляем email
            }
        }
    }
}
