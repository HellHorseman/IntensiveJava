package ru.y_lab.menu;

import lombok.AllArgsConstructor;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.model.Transaction;
import ru.y_lab.model.User;
import ru.y_lab.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class TransactionMenu {
    private final TransactionService transactionService;
    private final Scanner scanner;

    public void show(User user) {
        while (true) {
            System.out.println("1. Добавить транзакцию");
            System.out.println("2. Просмотреть транзакции");
            System.out.println("3. Редактировать транзакцию");
            System.out.println("4. Удалить транзакцию");
            System.out.println("5. Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addTransaction(user);
                    break;
                case 2:
                    viewTransactions(user);
                    break;
                case 3:
                    editTransaction(user);
                    break;
                case 4:
                    deleteTransaction(user);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private void addTransaction(User user) {
        System.out.println("Введите сумму:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Введите ID категории:");
        Long categoryId = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите дату (YYYY-MM-DD):");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("Введите описание:");
        String description = scanner.nextLine();
        System.out.println("Введите тип (income/expense):");
        String type = scanner.nextLine();

        if (transactionService.addTransaction(user.getId(), BigDecimal.valueOf(amount), categoryId, date, description, TransactionType.valueOf(type))) {
            System.out.println("Транзакция успешно добавлена");
        } else {
            System.out.println("Ошибка при добавлении транзакции");
        }
    }

    private void viewTransactions(User user) {
        List<Transaction> transactions = transactionService.getTransactions(user.getId());
        for (Transaction transaction : transactions) {
            System.out.println("ID: " + transaction.getId() +
                    ", Сумма: " + transaction.getAmount() +
                    ", Категория ID: " + transaction.getCategoryId() +
                    ", Дата: " + transaction.getDate() +
                    ", Описание: " + transaction.getDescription() +
                    ", Тип: " + transaction.getType());
        }
    }

    private void editTransaction(User user) {
        System.out.println("Введите ID транзакции:");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите новую сумму:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Введите новый ID категории:");
        Long categoryId = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите новое описание:");
        String description = scanner.nextLine();

        if (transactionService.editTransaction(id, BigDecimal.valueOf(amount), categoryId, description)) {
            System.out.println("Транзакция успешно изменена");
        } else {
            System.out.println("Ошибка при изменении транзакции");
        }
    }

    private void deleteTransaction(User user) {
        System.out.println("Введите ID транзакции:");
        Long id = scanner.nextLong();
        scanner.nextLine();

        if (transactionService.deleteTransaction(id)) {
            System.out.println("Транзакция успешно удалена");
        } else {
            System.out.println("Ошибка при удалении транзакции");
        }
    }
}
