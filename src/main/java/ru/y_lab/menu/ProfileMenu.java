package ru.y_lab.menu;

import ru.y_lab.actions.UserActions;

import java.util.Scanner;

import static ru.y_lab.menu.MainMenu.showMainMenu;

public class ProfileMenu {

    static void showProfileMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Профиль пользователя ---");
            System.out.println("1. Обновить профиль");
            System.out.println("2. Удалить аккаунт");
            System.out.println("3. Назад");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> UserActions.updateProfile(scanner); // Обновляем профиль
                case 2 -> {
                    UserActions.deleteAccount(); // Удаляем аккаунт
                    showMainMenu(scanner); // Возвращаемся в меню после удаления аккаунта
                }
                case 3 -> {
                    return; // Возвращаемся в главное меню
                }
                default -> System.out.println("Некорректный ввод.");
            }
        }
    }

}
