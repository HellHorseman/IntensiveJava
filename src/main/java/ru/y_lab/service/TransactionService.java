package ru.y_lab.service;

import ru.y_lab.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    private List<Transaction> transactions;

    public TransactionService() {
        transactions = new ArrayList<>();
    }

    public boolean addTransaction(String userId, double amount, String category, String date, String description, String type) {
        String id = UUID.randomUUID().toString();
        transactions.add(new Transaction(id, userId, amount, category, date, description, type));
        return true;
    }

    public List<Transaction> getTransactions(String userId) {
        List<Transaction> userTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getUserId().equals(userId)) {
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }

    public boolean editTransaction(String transactionId, double amount, String category, String description) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(transactionId)) {
                transaction.setAmount(amount);
                transaction.setCategory(category);
                transaction.setDescription(description);
                return true;
            }
        }
        return false;
    }

    public boolean deleteTransaction(String transactionId) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(transactionId)) {
                transactions.remove(transaction);
                return true;
            }
        }
        return false;
    }
}
