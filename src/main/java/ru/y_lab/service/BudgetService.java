package ru.y_lab.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.y_lab.model.Budget;
import ru.y_lab.repository.BudgetRepository;

import java.math.BigDecimal;

@AllArgsConstructor
public class BudgetService {
    private BudgetRepository budgetRepository;

    // Установка месячного бюджета
    public boolean setMonthlyBudget(Long userId, BigDecimal monthlyBudget) {
        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setMonthlyBudget(monthlyBudget);

        budgetRepository.save(budget);
        return true;
    }

    // Получение бюджета пользователя
    public Budget getBudget(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    // Обновление месячного бюджета
    public boolean updateMonthlyBudget(Long userId, BigDecimal monthlyBudget) {
        Budget budget = budgetRepository.findByUserId(userId);
        if (budget != null) {
            budget.setMonthlyBudget(monthlyBudget);
            budgetRepository.update(budget);
            return true;
        }
        return false;
    }

    // Удаление бюджета
    public boolean deleteBudget(Long userId) {
        Budget budget = budgetRepository.findByUserId(userId);
        if (budget != null) {
            budgetRepository.delete(budget.getId());
            return true;
        }
        return false;
    }
}