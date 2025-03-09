package ru.y_lab.service;

import ru.y_lab.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private final List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;

    public Transaction createTransaction(int userId, double amount, String category, LocalDate date, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной.");
        }
        Transaction transaction = new Transaction(nextId++, userId, amount, category, date, description);
        transactions.add(transaction);
        return transaction;
    }

    public Transaction getTransactionById(int transactionId, int userId) {
        return transactions.stream()
                .filter(t -> t.getId() == transactionId && t.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }

    public Transaction updateTransaction(int transactionId, int userId, double newAmount, String newCategory, String newDescription) {
        Transaction transaction = getTransactionById(transactionId, userId);
        if (transaction != null) {
            if (newAmount <= 0) {
                throw new IllegalArgumentException("Ошибка: сумма должна быть положительной.");
            }
            transaction.setAmount(newAmount);
            transaction.setCategory(newCategory);
            transaction.setDescription(newDescription);
            return transaction; // Возвращаем обновленный объект
        }
        return null; // Если транзакция не найдена
    }

    public boolean deleteTransaction(int transactionId, int userId) {
        return transactions.removeIf(t -> t.getId() == transactionId && t.getUserId() == userId);
    }

    public List<Transaction> getUserTransactions(int userId) {
        return transactions.stream()
                .filter(t -> t.getUserId() == userId)
                .toList();
    }
}
