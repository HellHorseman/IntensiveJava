package ru.y_lab.actions;

import ru.y_lab.model.Transaction;
import ru.y_lab.service.TransactionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TransactionActions {
    private static final TransactionService transactionService = new TransactionService();

    public static void addTransaction(Scanner scanner) {
        if (!UserActions.isLoggedIn()) {
            System.out.println("Ошибка: пользователь не авторизован.");
            return;
        }

        System.out.print("Введите сумму: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Ошибка: введите корректное число.");
            scanner.next();
        }
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println("Ошибка: сумма должна быть положительной.");
            return;
        }

        System.out.print("Введите категорию: ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) {
            System.out.println("Ошибка: категория не может быть пустой.");
            return;
        }

        System.out.print("Введите описание: ");
        String description = scanner.nextLine().trim();

        Transaction transaction = transactionService.createTransaction(
                UserActions.getCurrentUser().getId(), amount, category, LocalDate.now(), description
        );

        System.out.println("Транзакция добавлена: " + transaction);
    }

    public static void showTransactions() {
        if (!UserActions.isLoggedIn()) {
            System.out.println("Ошибка: пользователь не авторизован.");
            return;
        }

        List<Transaction> transactions = transactionService.getUserTransactions(UserActions.getCurrentUser().getId());
        if (transactions.isEmpty()) {
            System.out.println("Нет транзакций.");
        } else {
            transactions.forEach(System.out::println);
        }
    }

    public static void deleteTransaction(Scanner scanner) {
        if (!UserActions.isLoggedIn()) {
            System.out.println("Ошибка: пользователь не авторизован.");
            return;
        }

        System.out.print("Введите ID транзакции для удаления: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка: введите корректный ID.");
            scanner.next();
        }
        int transactionId = scanner.nextInt();
        scanner.nextLine();

        if (transactionService.deleteTransaction(transactionId, UserActions.getCurrentUser().getId())) {
            System.out.println("Транзакция удалена.");
        } else {
            System.out.println("Ошибка: не удалось удалить транзакцию.");
        }
    }

    public static void updateTransaction(Scanner scanner) {
        if (!UserActions.isLoggedIn()) {
            System.out.println("Ошибка: пользователь не авторизован.");
            return;
        }

        System.out.print("Введите ID транзакции для обновления: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка: введите корректный ID.");
            scanner.next();
        }
        int transactionId = scanner.nextInt();
        scanner.nextLine();

        Transaction existingTransaction = transactionService.getTransactionById(transactionId, UserActions.getCurrentUser().getId());
        if (existingTransaction == null) {
            System.out.println("Ошибка: транзакция не найдена.");
            return;
        }

        System.out.print("Введите новую сумму (оставьте пустым для сохранения текущей): ");
        String amountInput = scanner.nextLine().trim();
        double newAmount = existingTransaction.getAmount();
        if (!amountInput.isEmpty()) {
            try {
                newAmount = Double.parseDouble(amountInput);
                if (newAmount <= 0) {
                    System.out.println("Ошибка: сумма должна быть положительной.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число.");
                return;
            }
        }

        System.out.print("Введите новую категорию (оставьте пустым для сохранения текущей): ");
        String newCategory = scanner.nextLine().trim();
        if (newCategory.isEmpty()) {
            newCategory = existingTransaction.getCategory();
        }

        System.out.print("Введите новое описание (оставьте пустым для сохранения текущего): ");
        String newDescription = scanner.nextLine().trim();
        if (newDescription.isEmpty()) {
            newDescription = existingTransaction.getDescription();
        }

        Transaction updatedTransaction = transactionService.updateTransaction(
                transactionId, UserActions.getCurrentUser().getId(), newAmount, newCategory, newDescription
        );

        if (updatedTransaction != null) {
            System.out.println("Транзакция обновлена: " + updatedTransaction);
        } else {
            System.out.println("Ошибка: не удалось обновить транзакцию.");
        }
    }
}
