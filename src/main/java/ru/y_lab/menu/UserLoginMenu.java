package ru.y_lab.menu;

import ru.y_lab.actions.UserActions;

import java.util.Scanner;

import static ru.y_lab.menu.BudgetMenu.showBudgetMenu;
import static ru.y_lab.menu.GoalMenu.showGoalMenu;
import static ru.y_lab.menu.ProfileMenu.showProfileMenu;
import static ru.y_lab.menu.TransactionMenu.showTransactionMenu;

public class UserLoginMenu {

    static void showUserLoginMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Личный кабинет ---");
            System.out.println("1. Профиль");
            System.out.println("2. Управление транзакциями");
            System.out.println("3. Управление бюджетом");
            System.out.println("4. Управление целями");
            System.out.println("5. Выйти");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showProfileMenu(scanner);
                case 2 -> showTransactionMenu(scanner);
                case 3 -> showBudgetMenu(scanner);
                case 4 -> showGoalMenu(scanner);
                case 5 -> {
                    UserActions.logout(); // Разлогиниваем пользователя
                    return; // Возвращаемся в главное меню
                }
                default -> System.out.println("Некорректный ввод.");
            }
        }
    }
}
