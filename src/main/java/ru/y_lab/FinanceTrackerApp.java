package ru.y_lab;

import ru.y_lab.model.User;
import ru.y_lab.service.UserService;

import java.util.Optional;
import java.util.Scanner;

public class FinanceTrackerApp {
    private static final UserService userService = new UserService();
    private static User currentUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (currentUser == null) {
                System.out.println("\n--- Меню ---");
                System.out.println("1. Регистрация");
                System.out.println("2. Вход");
                System.out.println("3. Выход из программы");
                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> register(scanner);
                    case 2 -> login(scanner);
                    case 3 -> {
                        System.out.println("Выход из программы...");
                        return;
                    }
                    default -> System.out.println("Некорректный ввод. Попробуйте снова.");
                }
            } else {
                System.out.println("\n--- Личный кабинет ---");
                System.out.println("1. Показать профиль");
                System.out.println("2. Изменить данные");
                System.out.println("3. Удалить аккаунт");
                System.out.println("4. Выйти");
                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showProfile();
                    case 2 -> updateProfile(scanner);
                    case 3 -> {
                        deleteAccount();
                        currentUser = null;
                    }
                    case 4 -> {
                        currentUser = null;
                        System.out.println("Вы вышли из системы.");
                    }
                    default -> System.out.println("Некорректный ввод. Попробуйте снова.");
                }
            }
        }
    }

    private static void register(Scanner scanner) {
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

    private static void login(Scanner scanner) {
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        Optional<User> user = userService.login(email, password);
        if (user.isPresent()) {
            currentUser = user.get();
            System.out.println("Вы успешно вошли в систему.");
        } else {
            System.out.println("Ошибка: Неверный email или пароль.");
        }
    }

    private static void showProfile() {
        System.out.println("Ваш профиль:");
        System.out.println("Имя: " + currentUser.getName());
        System.out.println("Email: " + currentUser.getEmail());
    }

    private static void updateProfile(Scanner scanner) {
        System.out.print("Введите новое имя: ");
        String newName = scanner.nextLine();
        System.out.print("Введите новый email: ");
        String newEmail = scanner.nextLine();
        System.out.print("Введите новый пароль: ");
        String newPassword = scanner.nextLine();

        if (userService.updateUser(currentUser.getId(), newName, newEmail, newPassword)) {
            currentUser = new User(newName, newEmail, newPassword);
            System.out.println("Данные обновлены.");
        }
    }

    private static void deleteAccount() {
        if (userService.deleteUser(currentUser.getId())) {
            System.out.println("Ваш аккаунт удалён.");
        } else {
            System.out.println("Ошибка при удалении аккаунта.");
        }
    }
}
