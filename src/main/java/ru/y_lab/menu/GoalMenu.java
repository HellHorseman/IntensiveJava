package ru.y_lab.menu;

import lombok.AllArgsConstructor;
import ru.y_lab.model.User;
import ru.y_lab.model.Goal;
import ru.y_lab.service.GoalService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class GoalMenu {
    private final GoalService goalService;
    private final Scanner scanner;

    public void show(User user) {
        while (true) {
            System.out.println("1. Добавить цель");
            System.out.println("2. Просмотреть цели");
            System.out.println("3. Редактировать цель");
            System.out.println("4. Удалить цель");
            System.out.println("5. Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addGoal(user);
                    break;
                case 2:
                    viewGoals(user);
                    break;
                case 3:
                    editGoal(user);
                    break;
                case 4:
                    deleteGoal(user);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void addGoal(User user) {
        System.out.println("Введите название цели:");
        String name = scanner.nextLine();
        System.out.println("Введите целевую сумму:");
        double targetAmount = scanner.nextDouble();
        scanner.nextLine();

        if (goalService.addGoal(user.getId(), name, BigDecimal.valueOf(targetAmount))) {
            System.out.println("Цель успешно добавлена");
        } else {
            System.out.println("Ошибка при добавлении цели");
        }
    }

    private void viewGoals(User user) {
        List<Goal> goals = goalService.getGoals(user.getId());
        for (Goal goal : goals) {
            System.out.println("ID: " + goal.getId() +
                    ", Название: " + goal.getName() +
                    ", Целевая сумма: " + goal.getTargetAmount() +
                    ", Текущая сумма: " + goal.getCurrentAmount());
        }
    }

    private void editGoal(User user) {
        System.out.println("Введите ID цели:");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите новое название:");
        String name = scanner.nextLine();
        System.out.println("Введите новую целевую сумму:");
        double targetAmount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Введите текущую сумму:");
        double currentAmount = scanner.nextDouble();
        scanner.nextLine();

        if (goalService.updateGoal(id, name, BigDecimal.valueOf(targetAmount), BigDecimal.valueOf(currentAmount))) {
            System.out.println("Цель успешно обновлена");
        } else {
            System.out.println("Ошибка при обновлении цели");
        }
    }

    private void deleteGoal(User user) {
        System.out.println("Введите ID цели:");
        Long id = scanner.nextLong();
        scanner.nextLine();

        if (goalService.deleteGoal(id)) {
            System.out.println("Цель успешно удалена");
        } else {
            System.out.println("Ошибка при удалении цели");
        }
    }
}
