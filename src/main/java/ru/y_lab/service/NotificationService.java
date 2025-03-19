package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.model.Budget;
import ru.y_lab.model.Goal;
import ru.y_lab.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для отправки уведомлений пользователям.
 * Предоставляет методы для проверки превышения бюджета и прогресса по целям.
 */
@AllArgsConstructor
public class NotificationService {
    private BudgetService budgetService;
    private TransactionService transactionService;
    private GoalService goalService;
    private EmailService emailService;

    /**
     * Проверяет, превышен ли месячный бюджет пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param email  Электронная почта пользователя для отправки уведомления.
     */
    public void checkBudgetExceeded(Long userId, String email) {
        Budget budget = budgetService.getBudget(userId);
        if (budget == null) {
            System.out.println("Бюджет не установлен для пользователя с ID: " + userId);
            return;
        }

        BigDecimal totalExpenses = transactionService.getTransactions(userId).stream()
                .filter(t -> t.getType().equals("expense"))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalExpenses.compareTo(budget.getMonthlyBudget()) > 0) {
            String message = String.format(
                    "Внимание! Вы превысили месячный бюджет. Текущие расходы: %s, Бюджет: %s",
                    totalExpenses, budget.getMonthlyBudget()
            );
            System.out.println(message);
            emailService.sendEmail(email, "Превышение бюджета", message);
        } else {
            System.out.println("Вы в пределах бюджета.");
        }
    }

    /**
     * Проверяет прогресс по целям пользователя и отправляет уведомления.
     *
     * @param userId Идентификатор пользователя.
     * @param email  Электронная почта пользователя для отправки уведомления.
     */
    public void checkGoalProgress(Long userId, String email) {
        List<Goal> goals = goalService.getGoals(userId);
        if (goals.isEmpty()) {
            System.out.println("Цели не установлены для пользователя с ID: " + userId);
            return;
        }

        for (Goal goal : goals) {
            BigDecimal progressPercentage = calculateProgressPercentage(goal);
            String message = String.format(
                    "Цель: %s, Прогресс: %.2f%%",
                    goal.getName(), progressPercentage
            );
            System.out.println(message);

            if (progressPercentage.compareTo(new BigDecimal("100")) >= 0) {
                emailService.sendEmail(email, "Достигнут прогресс по цели", message);
            }
        }
    }

    /**
     * Рассчитывает прогресс по цели в процентах.
     *
     * @param goal Цель для расчета прогресса.
     * @return Прогресс в процентах.
     */
    private BigDecimal calculateProgressPercentage(Goal goal) {
        if (goal.getTargetAmount().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return goal.getCurrentAmount()
                .divide(goal.getTargetAmount(), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));
    }
}
