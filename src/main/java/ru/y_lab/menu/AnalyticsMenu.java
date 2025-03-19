package ru.y_lab.menu;

import lombok.AllArgsConstructor;
import ru.y_lab.model.User;
import ru.y_lab.service.AnalyticsService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

@AllArgsConstructor
public class AnalyticsMenu {
    private final AnalyticsService analyticsService;
    private final Scanner scanner;

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
        BigDecimal balance = analyticsService.calculateBalance(user.getId());
        System.out.println("Ваш текущий баланс: " + balance);
    }

    private void showTotalIncome(User user) {
        System.out.println("Введите начальную дату (YYYY-MM-DD):");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Введите конечную дату (YYYY-MM-DD):");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        BigDecimal totalIncome = analyticsService.calculateTotalIncome(user.getId(), startDate, endDate);
        System.out.println("Суммарный доход за период: " + totalIncome);
    }

    private void showTotalExpense(User user) {
        System.out.println("Введите начальную дату (YYYY-MM-DD):");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Введите конечную дату (YYYY-MM-DD):");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        BigDecimal totalExpense = analyticsService.calculateTotalExpense(user.getId(), startDate, endDate);
        System.out.println("Суммарный расход за период: " + totalExpense);
    }

    private void analyzeExpensesByCategory(User user) {
        Map<Long, BigDecimal> expensesByCategory = analyticsService.analyzeExpensesByCategory(user.getId());
        System.out.println("Расходы по категориям:");
        expensesByCategory.forEach((categoryId, amount) ->
                System.out.println("Категория ID " + categoryId + ": " + amount)
        );
    }
}