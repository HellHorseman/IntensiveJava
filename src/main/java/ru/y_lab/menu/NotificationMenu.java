package ru.y_lab.menu;

import lombok.AllArgsConstructor;
import ru.y_lab.model.User;
import ru.y_lab.service.NotificationService;

import java.util.Scanner;

@AllArgsConstructor
public class NotificationMenu {
    private final NotificationService notificationService;
    private final Scanner scanner;

    public void show(User user) {
        while (true) {
            System.out.println("1. Проверить превышение бюджета");
            System.out.println("2. Проверить прогресс по целям");
            System.out.println("3. Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    notificationService.checkBudgetExceeded(user.getId(), user.getEmail());
                    break;
                case 2:
                    notificationService.checkGoalProgress(user.getId(), user.getEmail());
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }
}
