package ru.y_lab.menu;

import ru.y_lab.actions.UserActions;
import ru.y_lab.service.UserService;

import java.util.Scanner;

import static ru.y_lab.menu.UserLoginMenu.showUserLoginMenu;

public class MainMenu {
    private static final UserService userService = new UserService();

    public static void showMainMenu(Scanner scanner) {
        while (true) {
            if (!UserActions.isLoggedIn()) {
                // Меню, если пользователь не залогинен
                System.out.println("\n--- Меню ---");
                System.out.println("1. Регистрация");
                System.out.println("2. Вход");
                System.out.println("3. Выход");
                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> UserActions.register(scanner);
                    case 2 -> {
                        UserActions.login(scanner);
                        if (UserActions.isLoggedIn()) {
                            // Если логин успешен, переходим в меню профиля
                            showUserLoginMenu(scanner);
                        }
                    }
                    case 3 -> {
                        System.out.println("Выход из программы...");
                        return; // Выход из программы
                    }
                    default -> System.out.println("Некорректный ввод.");
                }
            } else {
                // Меню после успешного входа
                showUserLoginMenu(scanner);
            }
        }
    }


}
