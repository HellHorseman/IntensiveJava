package ru.y_lab.actions;

import ru.y_lab.model.User;
import ru.y_lab.service.UserService;

import java.util.Optional;
import java.util.Scanner;

public class UserActions {
    private static final UserService userService = new UserService();
    private static User currentUser;  // Для отслеживания текущего пользователя
    private static boolean isLoggedIn = false; // Флаг залогинен ли пользователь

    public static void register(Scanner scanner) {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (userService.register(name, email, password)) {
            System.out.println("Регистрация успешна!");
        }
    }

    public static void login(Scanner scanner) {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        Optional<User> user = userService.login(email, password);
        if (user.isPresent()) {
            currentUser = user.get(); // Устанавливаем текущего пользователя
            isLoggedIn = true; // Флаг успешного входа
            System.out.println("Вы успешно вошли в систему.");
        } else {
            System.out.println("Ошибка: Неверный email или пароль.");
        }
    }

    public static void showProfile() {
        if (isLoggedIn && currentUser != null) {
            System.out.println("Ваш профиль:");
            System.out.println("Имя: " + currentUser.getName());
            System.out.println("Email: " + currentUser.getEmail());
        } else {
            System.out.println("Ошибка: Пользователь не авторизован.");
        }
    }

    public static void updateProfile(Scanner scanner) {
        if (isLoggedIn && currentUser != null) {
            System.out.print("Введите новое имя: ");
            String newName = scanner.nextLine();
            System.out.print("Введите новый email: ");
            String newEmail = scanner.nextLine();
            System.out.print("Введите новый пароль: ");
            String newPassword = scanner.nextLine();

            if (userService.updateUser(currentUser.getId(), newName, newEmail, newPassword)) {
                currentUser = new User(newName, newEmail, newPassword); // Обновляем данные пользователя
                System.out.println("Данные обновлены.");
            } else {
                System.out.println("Ошибка при обновлении данных.");
            }
        } else {
            System.out.println("Ошибка: Пользователь не авторизован.");
        }
    }

    public static void deleteAccount() {
        if (isLoggedIn && currentUser != null) {
            if (userService.deleteUser(currentUser.getId())) {
                System.out.println("Ваш аккаунт удалён.");
                currentUser = null; // Разлогиниваем пользователя
                isLoggedIn = false; // Обнуляем флаг
            } else {
                System.out.println("Ошибка при удалении аккаунта.");
            }
        } else {
            System.out.println("Ошибка: Пользователь не авторизован.");
        }
    }

    // Геттеры и сеттеры
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
        isLoggedIn = false;
        System.out.println("Вы вышли из системы.");
    }
}
