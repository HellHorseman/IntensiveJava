package ru.y_lab.actions;

import ru.y_lab.service.GoalService;

import java.util.Scanner;

public class GoalActions {

    private static GoalService goalService;

    public static void setGoal(Scanner scanner) {
        System.out.print("Введите цель накоплений: ");
        double targetAmount = scanner.nextDouble();
        scanner.nextLine();
        goalService = new GoalService(targetAmount);
        System.out.println("Цель установлена.");
    }

    public static void addProgress(Scanner scanner) {
        System.out.print("Введите сумму прогресса: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        goalService.addProgress(amount);
        System.out.println("Прогресс добавлен.");
    }

    public static void checkGoal() {
        if (goalService.isGoalAchieved()) {
            System.out.println("Поздравляем! Цель достигнута.");
        } else {
            System.out.println("Цель еще не достигнута.");
        }
    }
}
