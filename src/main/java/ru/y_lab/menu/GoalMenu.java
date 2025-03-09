package ru.y_lab.menu;

import ru.y_lab.actions.GoalActions;

import java.util.Scanner;

public class GoalMenu {

    public static void showGoalMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Управление целями ---");
            System.out.println("1. Установить цель");
            System.out.println("2. Добавить прогресс");
            System.out.println("3. Проверить достижение цели");
            System.out.println("4. Назад");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> GoalActions.setGoal(scanner);
                case 2 -> GoalActions.addProgress(scanner);
                case 3 -> GoalActions.checkGoal();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Некорректный ввод.");
            }
        }
    }
}
