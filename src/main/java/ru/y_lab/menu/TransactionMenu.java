package ru.y_lab.menu;

import ru.y_lab.model.Transaction;
import ru.y_lab.model.User;
import ru.y_lab.service.TransactionService;

import java.util.List;
import java.util.Scanner;

public class TransactionMenu {
    private TransactionService transactionService;
    private Scanner scanner;

    public TransactionMenu(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.scanner = new Scanner(System.in);
    }

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

    public void addTransaction(User user) {
        System.out.println("Введите сумму:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        System.out.println("Введите дату (YYYY-MM-DD):");
        String date = scanner.nextLine();
        System.out.println("Введите описание:");
        String description = scanner.nextLine();
        System.out.println("Введите тип (income/expense):");
        String type = scanner.nextLine();

        if (transactionService.addTransaction(user.getEmail(), amount, category, date, description, type)) {
            System.out.println("Транзакция успешно добавлена");
        } else {
            System.out.println("Ошибка при добавлении транзакции");
        }
    }

    public void viewTransactions(User user) {
        List<Transaction> transactions = transactionService.getTransactions(user.getEmail());
        for (Transaction transaction : transactions) {
            System.out.println("ID: " + transaction.getId() + ", Сумма: " + transaction.getAmount() + ", Категория: " + transaction.getCategory() + ", Дата: " + transaction.getDate() + ", Описание: " + transaction.getDescription() + ", Тип: " + transaction.getType());
        }
    }

    public void editTransaction(User user) {
        System.out.println("Введите ID транзакции:");
        String id = scanner.nextLine();
        System.out.println("Введите новую сумму:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.println("Введите новую категорию:");
        String category = scanner.nextLine();
        System.out.println("Введите новое описание:");
        String description = scanner.nextLine();

        if (transactionService.editTransaction(id, amount, category, description)) {
            System.out.println("Транзакция успешно изменена");
        } else {
            System.out.println("Ошибка при изменении транзакции");
        }
    }

    public void deleteTransaction(User user) {
        System.out.println("Введите ID транзакции:");
        String id = scanner.nextLine();

        if (transactionService.deleteTransaction(id)) {
            System.out.println("Транзакция успешно удалена");
        } else {
            System.out.println("Ошибка при удалении транзакции");
        }
    }
}
