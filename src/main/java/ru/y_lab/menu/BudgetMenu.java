package ru.y_lab.menu;

import ru.y_lab.actions.BudgetActions;

import java.util.Scanner;

public class BudgetMenu {
    public static void showBudgetMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Управление бюджетом ---");
            System.out.println("1. Установить месячный бюджет");
            System.out.println("2. Проверить бюджет");
            System.out.println("3. Назад");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> BudgetActions.setBudget(scanner);
                case 2 -> BudgetActions.checkOverBudget();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Некорректный ввод.");
            }
        }
    }
}
