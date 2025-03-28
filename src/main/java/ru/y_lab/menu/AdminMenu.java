package ru.y_lab.menu;

import ru.y_lab.model.Transaction;
import ru.y_lab.model.User;
import ru.y_lab.service.TransactionService;
import ru.y_lab.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminMenu {
    private final UserService userService;
    private final TransactionService transactionService;
    private final Scanner scanner;

    public AdminMenu(UserService userService, TransactionService transactionService, Scanner scanner) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.scanner = scanner;
    }

    public void show(User admin) {
        if (!admin.isAdmin()) {
            System.out.println("Доступ запрещен. Вы не администратор.");
            return;
        }

        while (true) {
            System.out.println("1. Просмотреть список пользователей");
            System.out.println("2. Просмотреть транзакции пользователя");
            System.out.println("3. Заблокировать/разблокировать пользователя");
            System.out.println("4. Удалить пользователя");
            System.out.println("5. Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    viewUserTransactions();
                    break;
                case 3:
                    toggleUserBlock();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void viewAllUsers() {
        Map<String, User> users = userService.getAllUsers();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            System.out.println("Email: " + user.getEmail() + ", Имя: " + user.getName() + ", Админ: " + user.isAdmin());
        }
    }

    private void viewUserTransactions() {
        System.out.println("Введите email пользователя:");
        String email = scanner.nextLine();

        List<Transaction> transactions = transactionService.getTransactions(email);
        if (transactions.isEmpty()) {
            System.out.println("Транзакции не найдены.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println("ID: " + transaction.getId() +
                    ", Сумма: " + transaction.getAmount() +
                    ", Категория: " + transaction.getCategory() +
                    ", Дата: " + transaction.getDate() +
                    ", Тип: " + transaction.getType());
        }
    }

    private void toggleUserBlock() {
        System.out.println("Введите email пользователя:");
        String email = scanner.nextLine();

        if (userService.toggleUserBlock(email)) {
            System.out.println("Статус пользователя изменен.");
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    private void deleteUser() {
        System.out.println("Введите email пользователя:");
        String email = scanner.nextLine();

        if (userService.deleteUser(email)) {
            System.out.println("Пользователь успешно удален.");
        } else {
            System.out.println("Пользователь не найден.");
        }
    }
}
