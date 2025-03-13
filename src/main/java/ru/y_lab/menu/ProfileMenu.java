package ru.y_lab.menu;


import ru.y_lab.model.User;
import ru.y_lab.service.UserService;

import java.util.Scanner;

public class ProfileMenu {
    private UserService userService;
    private Scanner scanner;

    public ProfileMenu(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void show(User user) {
        while (true) {
            System.out.println("1. Редактировать имя");
            System.out.println("2. Редактировать email");
            System.out.println("3. Редактировать пароль");
            System.out.println("4. Удалить аккаунт");
            System.out.println("5. Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    editName(user);
                    break;
                case 2:
                    editEmail(user);
                    break;
                case 3:
                    editPassword(user);
                    break;
                case 4:
                    deleteAccount(user);
                    return;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void editName(User user) {
        System.out.println("Введите новое имя:");
        String name = scanner.nextLine();
        user.setName(name);
        System.out.println("Имя успешно изменено");
    }

    private void editEmail(User user) {
        System.out.println("Введите новый email:");
        String email = scanner.nextLine();
        if (userService.updateUser(user.getEmail(), user.getName(), user.getPassword())) {
            System.out.println("Email успешно изменен");
        } else {
            System.out.println("Ошибка при изменении email");
        }
    }

    private void editPassword(User user) {
        System.out.println("Введите новый пароль:");
        String password = scanner.nextLine();
        user.setPassword(password);
        System.out.println("Пароль успешно изменен");
    }

    private void deleteAccount(User user) {
        if (userService.deleteUser(user.getEmail())) {
            System.out.println("Аккаунт успешно удален");
        } else {
            System.out.println("Ошибка при удалении аккаунта");
        }
    }
}
