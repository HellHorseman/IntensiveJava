package ru.y_lab.menu;

import ru.y_lab.model.User;
import ru.y_lab.service.UserService;

import java.util.Scanner;

public class MainMenu {
    private final UserService userService;
    private final UserMenu userMenu;
    private final AdminMenu adminMenu;
    private final Scanner scanner;

    public MainMenu(UserService userService, UserMenu userMenu, AdminMenu adminMenu, Scanner scanner) {
        this.userService = userService;
        this.userMenu = userMenu;
        this.adminMenu = adminMenu;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void registerUser() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        System.out.println("Это администратор? (да/нет):");
        boolean isAdmin = scanner.nextLine().equalsIgnoreCase("да");

        if (userService.registerUser(email, password, name, isAdmin)) {
            System.out.println("Пользователь успешно зарегистрирован");
        } else {
            System.out.println("Пользователь с таким email уже существует");
        }
    }

    private void loginUser() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        User user = userService.login(email, password);
        if (user != null) {
            System.out.println("Вход выполнен успешно");
            if (user.isAdmin()) {
                adminMenu.show(user);
            } else {
                userMenu.show(user);
            }
        } else {
            System.out.println("Неверный email или пароль");
        }
    }
}
