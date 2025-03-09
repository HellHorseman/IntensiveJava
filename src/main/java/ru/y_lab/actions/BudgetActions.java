package ru.y_lab.actions;

import ru.y_lab.service.BudgetService;

import java.util.Scanner;

public class BudgetActions {

    private static BudgetService budgetService;

    public BudgetActions(BudgetService budgetService) {
        BudgetActions.budgetService = budgetService;
    }

    public static void setBudget(Scanner scanner) {
        if (!UserActions.isLoggedIn()) {
            System.out.println("Ошибка: пользователь не авторизован.");
            return;
        }

        System.out.print("Введите новый месячный бюджет: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Ошибка: введите корректное число.");
            scanner.nextLine();
            double budgetAmount = scanner.nextDouble();
            budgetService.setMonthlyBudget(budgetAmount);
            System.out.println("Месячный бюджет установлен.");
        }
    }

    public static void checkOverBudget() {
        if (budgetService.isOverBudget()) {
            System.out.println("Внимание! Вы превысили бюджет!");
        } else {
            System.out.println("Вы в пределах бюджета.");
        }
    }
}
