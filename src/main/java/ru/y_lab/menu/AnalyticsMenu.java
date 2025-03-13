package ru.y_lab.menu;

import ru.y_lab.model.User;
import ru.y_lab.service.AnalyticsService;

import java.util.Scanner;

public class AnalyticsMenu {
    private AnalyticsService analyticsService;
    private Scanner scanner;

    public AnalyticsMenu(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
        this.scanner = new Scanner(System.in);
    }

    public void show(User user) {
        while (true) {
            System.out.println("1. Показать текущий баланс");
            System.out.println("2. Показать суммарный доход за период");
            System.out.println("3. Показать суммарный расход за период");
            System.out.println("4. Анализ расходов по категориям");
            System.out.println("5. Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showBalance(user);
                    break;
                case 2:
                    showTotalIncome(user);
                    break;
                case 3:
                    showTotalExpense(user);
                    break;
                case 4:
                    analyzeExpensesByCategory(user);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void showBalance(User user) {
        double balance = analyticsService.calculateBalance(user.getEmail());
        System.out.println("Ваш текущий баланс: " + balance);
    }

    private void showTotalIncome(User user) {
        System.out.println("Введите начальную дату (YYYY-MM-DD):");
        String startDate = scanner.nextLine();
        System.out.println("Введите конечную дату (YYYY-MM-DD):");
        String endDate = scanner.nextLine();

        double totalIncome = analyticsService.calculateTotalIncome(user.getEmail(), startDate, endDate);
        System.out.println("Суммарный доход за период: " + totalIncome);
    }

    private void showTotalExpense(User user) {
        System.out.println("Введите начальную дату (YYYY-MM-DD):");
        String startDate = scanner.nextLine();
        System.out.println("Введите конечную дату (YYYY-MM-DD):");
        String endDate = scanner.nextLine();

        double totalExpense = analyticsService.calculateTotalExpense(user.getEmail(), startDate, endDate);
        System.out.println("Суммарный расход за период: " + totalExpense);
    }

    private void analyzeExpensesByCategory(User user) {
        analyticsService.analyzeExpensesByCategory(user.getEmail());
    }
}
