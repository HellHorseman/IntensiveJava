package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AnalyticsService {
    private TransactionService transactionService;

    // Расчёт баланса
    public BigDecimal calculateBalance(Long userId) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return transactions.stream()
                .map(transaction -> transaction.getType() == TransactionType.INCOME ? transaction.getAmount() : transaction.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Расчёт суммарного дохода за период
    public BigDecimal calculateTotalIncome(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .filter(transaction -> !transaction.getDate().isBefore(startDate) && !transaction.getDate().isAfter(endDate))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Расчёт суммарного расхода за период
    public BigDecimal calculateTotalExpense(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .filter(transaction -> !transaction.getDate().isBefore(startDate) && !transaction.getDate().isAfter(endDate))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Анализ расходов по категориям
    public Map<String, BigDecimal> analyzeExpensesByCategory(Long userId) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        Transaction::getCategoryId,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                );
    }
}
