package ru.y_lab.menu;

import ru.y_lab.actions.TransactionActions;

import java.util.Scanner;

public class TransactionMenu {

    public static void showTransactionMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Транзакции ---");
            System.out.println("1. Добавить транзакцию");
            System.out.println("2. Просмотреть все транзакции");
            System.out.println("3. Изменить транзакцию");
            System.out.println("4. Удалить транзакцию");
            System.out.println("5. Назад");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> TransactionActions.addTransaction(scanner);
                case 2 -> TransactionActions.showTransactions();
                case 3 -> TransactionActions.updateTransaction(scanner);
                case 4 -> TransactionActions.deleteTransaction(scanner);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Некорректный ввод.");
            }
        }
    }
}
