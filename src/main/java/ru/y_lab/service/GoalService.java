package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.model.Goal;
import ru.y_lab.repository.GoalRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для управления целями пользователей.
 * Предоставляет методы для добавления, обновления, удаления и получения целей.
 */
@AllArgsConstructor
public class GoalService {
    private GoalRepository goalRepository;

    /**
     * Добавляет новую цель.
     *
     * @param userId      Идентификатор пользователя.
     * @param name        Название цели.
     * @param targetAmount Целевая сумма.
     * @return {@code true}, если цель добавлена успешно.
     */
    public boolean addGoal(Long userId, String name, BigDecimal targetAmount) {
        Goal goal = new Goal();
        goal.setUserId(userId);
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setCurrentAmount(BigDecimal.ZERO);

        goalRepository.save(goal);
        return true;
    }

    /**
     * Возвращает список всех целей пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Список целей.
     */
    public List<Goal> getGoals(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    /**
     * Обновляет существующую цель.
     *
     * @param goalId        Идентификатор цели.
     * @param name          Новое название цели.
     * @param targetAmount  Новая целевая сумма.
     * @param currentAmount Текущая сумма.
     * @return {@code true}, если цель обновлена успешно.
     */
    public boolean updateGoal(Long goalId, String name, BigDecimal targetAmount, BigDecimal currentAmount) {
        Goal goal = new Goal();
        goal.setId(goalId);
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setCurrentAmount(currentAmount);

        goalRepository.update(goal);
        return true;
    }

    /**
     * Удаляет цель.
     *
     * @param goalId Идентификатор цели.
     * @return {@code true}, если цель удалена успешно.
     */
    public boolean deleteGoal(Long goalId) {
        goalRepository.delete(goalId);
        return true;
    }
}
