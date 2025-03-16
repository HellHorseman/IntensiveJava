package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.model.Goal;
import ru.y_lab.repository.GoalRepository;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class GoalService {
    private GoalRepository goalRepository;

    // Добавление новой цели
    public boolean addGoal(Long userId, String name, BigDecimal targetAmount) {
        Goal goal = new Goal();
        goal.setUserId(userId);
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setCurrentAmount(BigDecimal.ZERO); // Начальное значение текущей суммы

        goalRepository.save(goal);
        return true;
    }

    // Получение всех целей пользователя
    public List<Goal> getGoals(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    // Обновление цели
    public boolean updateGoal(Long goalId, String name, BigDecimal targetAmount, BigDecimal currentAmount) {
        Goal goal = new Goal();
        goal.setId(goalId);
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setCurrentAmount(currentAmount);

        goalRepository.update(goal);
        return true;
    }

    // Удаление цели
    public boolean deleteGoal(Long goalId) {
        goalRepository.delete(goalId);
        return true;
    }
}
