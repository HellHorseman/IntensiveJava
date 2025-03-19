package ru.y_lab.menu;


import lombok.AllArgsConstructor;
import ru.y_lab.model.User;
import ru.y_lab.service.UserService;

import java.util.Scanner;

@AllArgsConstructor
public class ProfileMenu {
    private final UserService userService;
    private final Scanner scanner;

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
        String newEmail = scanner.nextLine();
        System.out.println("Введите пароль для подтверждения:");
        String password = scanner.nextLine();

        if (user.getPassword().equals(password)) {
            if (userService.updateUserEmail(user.getEmail(), newEmail)) {
                user.setEmail(newEmail);
                System.out.println("Email успешно изменен");
            } else {
                System.out.println("Ошибка при изменении email");
            }
        } else {
            System.out.println("Неверный пароль");
        }
    }

    private void editPassword(User user) {
        System.out.println("Введите новый пароль:");
        String newPassword = scanner.nextLine();
        System.out.println("Введите старый пароль для подтверждения:");
        String oldPassword = scanner.nextLine();

        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            System.out.println("Пароль успешно изменен");
        } else {
            System.out.println("Неверный старый пароль");
        }
    }

    private void deleteAccount(User user) {
        System.out.println("Введите пароль для подтверждения:");
        String password = scanner.nextLine();

        if (user.getPassword().equals(password)) {
            if (userService.deleteUser(user.getId())) {
                System.out.println("Аккаунт успешно удален");
            } else {
                System.out.println("Ошибка при удалении аккаунта");
            }
        } else {
            System.out.println("Неверный пароль");
        }
    }
}
