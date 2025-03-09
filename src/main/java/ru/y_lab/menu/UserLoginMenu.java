package ru.y_lab.menu;

import ru.y_lab.actions.UserActions;

import java.util.Scanner;

import static ru.y_lab.menu.ProfileMenu.showProfileMenu;

public class UserLoginMenu {

    static void showUserLoginMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Личный кабинет ---");
            System.out.println("1. Профиль");
            System.out.println("2. Управление транзакциями");
            System.out.println("3. Выйти");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showProfileMenu(scanner);
                case 2 -> {
                    // Добавить сюда логику для управления транзакциями
                    System.out.println("Управление транзакциями");
                }
                case 3 -> {
                    UserActions.logout(); // Разлогиниваем пользователя
                    return; // Возвращаемся в главное меню
                }
                default -> System.out.println("Некорректный ввод.");
            }
        }
    }
}
