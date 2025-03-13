package ru.y_lab.menu;

import ru.y_lab.model.User;
import ru.y_lab.service.GoalService;
import ru.y_lab.service.NotificationService;

import java.util.Scanner;

public class NotificationMenu {
    private NotificationService notificationService;
    private GoalService goalService;
    private Scanner scanner;

    public NotificationMenu(NotificationService notificationService, GoalService goalService) {
        this.notificationService = notificationService;
        this.goalService = goalService;
        this.scanner = new Scanner(System.in);
    }

    public void show(User user) {
        while (true) {
            System.out.println("1. Проверить превышение бюджета");
            System.out.println("2. Проверить прогресс по целям");
            System.out.println("3. Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    notificationService.checkBudgetExceeded(user.getEmail(), user.getEmail());
                    break;
                case 2:
                    notificationService.checkGoalProgress(user.getEmail(), user.getEmail(), goalService);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }
}
