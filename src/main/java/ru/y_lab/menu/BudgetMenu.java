package ru.y_lab.menu;

import ru.y_lab.model.Budget;
import ru.y_lab.model.User;
import ru.y_lab.model.Transaction;
import ru.y_lab.service.BudgetService;
import ru.y_lab.service.TransactionService;

import java.util.Scanner;

public class BudgetMenu {
    private BudgetService budgetService;
    private TransactionService transactionService;
    private Scanner scanner;

    public BudgetMenu(BudgetService budgetService, TransactionService transactionService) {
        this.budgetService = budgetService;
        this.transactionService = transactionService;
        this.scanner = new Scanner(System.in);
    }

    public void show(User user) {
        while (true) {
            System.out.println("1. Установить месячный бюджет");
            System.out.println("2. Просмотреть текущий бюджет");
            System.out.println("3. Обновить месячный бюджет");
            System.out.println("4. Проверить превышение бюджета");
            System.out.println("5. Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    setMonthlyBudget(user);
                    break;
                case 2:
                    viewCurrentBudget(user);
                    break;
                case 3:
                    updateMonthlyBudget(user);
                    break;
                case 4:
                    checkBudgetExceeded(user);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void setMonthlyBudget(User user) {
        System.out.println("Введите месячный бюджет:");
        double monthlyBudget = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        budgetService.setMonthlyBudget(user.getEmail(), monthlyBudget);
        System.out.println("Месячный бюджет успешно установлен");
    }

    private void viewCurrentBudget(User user) {
        Budget budget = budgetService.getBudget(user.getEmail());
        if (budget != null) {
            System.out.println("Ваш текущий месячный бюджет: " + budget.getMonthlyBudget());
        } else {
            System.out.println("Бюджет не установлен");
        }
    }

    private void updateMonthlyBudget(User user) {
        System.out.println("Введите новый месячный бюджет:");
        double monthlyBudget = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        if (budgetService.updateMonthlyBudget(user.getEmail(), monthlyBudget)) {
            System.out.println("Месячный бюджет успешно обновлен");
        } else {
            System.out.println("Ошибка при обновлении бюджета");
        }
    }

    private void checkBudgetExceeded(User user) {
        Budget budget = budgetService.getBudget(user.getEmail());
        if (budget == null) {
            System.out.println("Бюджет не установлен");
            return;
        }

        double totalExpenses = transactionService.getTransactions(user.getEmail()).stream()
                .filter(t -> t.getType().equals("expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        if (totalExpenses > budget.getMonthlyBudget()) {
            System.out.println("Внимание! Вы превысили месячный бюджет.");
        } else {
            System.out.println("Вы в пределах бюджета.");
        }
    }
}
