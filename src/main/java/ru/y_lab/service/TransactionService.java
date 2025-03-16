package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.model.Transaction;
import ru.y_lab.repository.CategoryRepository;
import ru.y_lab.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class TransactionService {
    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;

    // Добавление новой транзакции
    public boolean addTransaction(Long userId, BigDecimal amount, Long categoryId, String date, String description, String type) {
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setCategoryId(categoryId);
        transaction.setDate(java.time.LocalDate.parse(date));
        transaction.setDescription(description);
        transaction.setType(TransactionType.valueOf(type));

        transactionRepository.save(transaction);
        return true;
    }

    // Получение всех транзакций пользователя
    public List<Transaction> getTransactions(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    // Редактирование транзакции
    public boolean editTransaction(Long transactionId, BigDecimal amount, Long categoryId, String description) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setAmount(amount);
        transaction.setCategoryId(categoryId);
        transaction.setDescription(description);

        transactionRepository.update(transaction);
        return true;
    }

    // Удаление транзакции
    public boolean deleteTransaction(Long transactionId) {
        transactionRepository.delete(transactionId);
        return true;
    }
}