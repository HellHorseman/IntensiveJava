package ru.y_lab.menu;

import lombok.AllArgsConstructor;
import ru.y_lab.model.User;
import ru.y_lab.service.TransactionService;

import java.util.Scanner;

@AllArgsConstructor
public class UserMenu {
    private final TransactionService transactionService;
    private final TransactionMenu transactionMenu;
    private final ProfileMenu profileMenu;
    private final BudgetMenu budgetMenu;
    private final GoalMenu goalMenu;
    private final AnalyticsMenu analyticsMenu;
    private final NotificationMenu notificationMenu;
    private final Scanner scanner;

    public void show(User user) {
        while (true) {
            System.out.println("1. Управление транзакциями");
            System.out.println("2. Управление профилем");
            System.out.println("3. Управление бюджетом");
            System.out.println("4. Управление целями");
            System.out.println("5. Аналитика");
            System.out.println("6. Уведомления");
            System.out.println("7. Выйти");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    transactionMenu.show(user);
                    break;
                case 2:
                    profileMenu.show(user);
                    break;
                case 3:
                    budgetMenu.show(user);
                    break;
                case 4:
                    goalMenu.show(user);
                    break;
                case 5:
                    analyticsMenu.show(user);
                    break;
                case 6:
                    notificationMenu.show(user);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }
}
