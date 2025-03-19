package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.model.Budget;
import ru.y_lab.repository.BudgetRepository;

import java.math.BigDecimal;

/**
 * Сервис для управления бюджетами пользователей.
 * Предоставляет методы для установки, обновления и удаления бюджетов.
 */
@AllArgsConstructor
public class BudgetService {
    private BudgetRepository budgetRepository;

    /**
     * Устанавливает месячный бюджет для пользователя.
     *
     * @param userId        Идентификатор пользователя.
     * @param monthlyBudget Месячный бюджет.
     * @return {@code true}, если бюджет установлен успешно.
     */
    public boolean setMonthlyBudget(Long userId, BigDecimal monthlyBudget) {
        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setMonthlyBudget(monthlyBudget);

        budgetRepository.save(budget);
        return true;
    }

    /**
     * Возвращает бюджет пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Объект бюджета или {@code null}, если бюджет не найден.
     */
    public Budget getBudget(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    /**
     * Обновляет месячный бюджет пользователя.
     *
     * @param userId        Идентификатор пользователя.
     * @param monthlyBudget Новый месячный бюджет.
     * @return {@code true}, если бюджет обновлен успешно.
     */
    public boolean updateMonthlyBudget(Long userId, BigDecimal monthlyBudget) {
        Budget budget = budgetRepository.findByUserId(userId);
        if (budget != null) {
            budget.setMonthlyBudget(monthlyBudget);
            budgetRepository.update(budget);
            return true;
        }
        return false;
    }

    /**
     * Удаляет бюджет пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return {@code true}, если бюджет удален успешно.
     */
    public boolean deleteBudget(Long userId) {
        Budget budget = budgetRepository.findByUserId(userId);
        if (budget != null) {
            budgetRepository.delete(budget.getId());
            return true;
        }
        return false;
    }
}