package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для аналитики финансовых данных.
 * Предоставляет методы для расчета баланса, доходов, расходов и анализа по категориям.
 */
@AllArgsConstructor
public class AnalyticsService {
    private TransactionService transactionService;

    /**
     * Рассчитывает баланс пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Баланс пользователя.
     */
    public BigDecimal calculateBalance(Long userId) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return transactions.stream()
                .map(transaction -> transaction.getType() == TransactionType.INCOME ? transaction.getAmount() : transaction.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Рассчитывает суммарный доход за указанный период.
     *
     * @param userId    Идентификатор пользователя.
     * @param startDate Начальная дата периода.
     * @param endDate   Конечная дата периода.
     * @return Суммарный доход за период.
     */
    public BigDecimal calculateTotalIncome(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .filter(transaction -> !transaction.getDate().isBefore(startDate) && !transaction.getDate().isAfter(endDate))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Рассчитывает суммарный расход за указанный период.
     *
     * @param userId    Идентификатор пользователя.
     * @param startDate Начальная дата периода.
     * @param endDate   Конечная дата периода.
     * @return Суммарный расход за период.
     */
    public BigDecimal calculateTotalExpense(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .filter(transaction -> !transaction.getDate().isBefore(startDate) && !transaction.getDate().isAfter(endDate))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Анализирует расходы по категориям.
     *
     * @param userId Идентификатор пользователя.
     * @return Карта, где ключ — идентификатор категории, а значение — сумма расходов по этой категории.
     */
    public Map<Long, BigDecimal> analyzeExpensesByCategory(Long userId) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        Transaction::getCategoryId,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));
    }
}
