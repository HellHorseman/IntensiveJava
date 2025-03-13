package ru.y_lab.service;

import ru.y_lab.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsService {
    private TransactionService transactionService;

    public AnalyticsService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public double calculateBalance(String userId) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        double balance = 0.0;
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("income")) {
                balance += transaction.getAmount();
            } else if (transaction.getType().equals("expense")) {
                balance -= transaction.getAmount();
            }
        }
        return balance;
    }

    // Расчёт суммарного дохода за период
    public double calculateTotalIncome(String userId, String startDate, String endDate) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        double totalIncome = 0.0;
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("income") &&
                    transaction.getDate().compareTo(startDate) >= 0 &&
                    transaction.getDate().compareTo(endDate) <= 0) {
                totalIncome += transaction.getAmount();
            }
        }
        return totalIncome;
    }

    // Расчёт суммарного расхода за период
    public double calculateTotalExpense(String userId, String startDate, String endDate) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        double totalExpense = 0.0;
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("expense") &&
                    transaction.getDate().compareTo(startDate) >= 0 &&
                    transaction.getDate().compareTo(endDate) <= 0) {
                totalExpense += transaction.getAmount();
            }
        }
        return totalExpense;
    }

    // Анализ расходов по категориям
    public void analyzeExpensesByCategory(String userId) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        Map<String, Double> categoryExpenses = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("expense")) {
                categoryExpenses.put(transaction.getCategory(),
                        categoryExpenses.getOrDefault(transaction.getCategory(), 0.0) + transaction.getAmount());
            }
        }

        System.out.println("Анализ расходов по категориям:");
        for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
