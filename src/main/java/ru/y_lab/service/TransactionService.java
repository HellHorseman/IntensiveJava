package ru.y_lab.service;

import lombok.AllArgsConstructor;
import ru.y_lab.enums.TransactionType;
import ru.y_lab.model.Transaction;
import ru.y_lab.repository.CategoryRepository;
import ru.y_lab.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Сервис для управления транзакциями.
 * Предоставляет методы для добавления, редактирования, удаления и получения транзакций.
 */
@AllArgsConstructor
public class TransactionService {
    private TransactionRepository transactionRepository;

    /**
     * Добавляет новую транзакцию.
     *
     * @param userId      Идентификатор пользователя.
     * @param amount      Сумма транзакции.
     * @param categoryId  Идентификатор категории.
     * @param date        Дата транзакции.
     * @param description Описание транзакции.
     * @param type        Тип транзакции (доход/расход).
     * @return {@code true}, если транзакция добавлена успешно.
     */
    public boolean addTransaction(Long userId, BigDecimal amount, Long categoryId, LocalDate date, String description, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setCategoryId(categoryId);
        transaction.setDate(date);
        transaction.setDescription(description);
        transaction.setType(type);

        transactionRepository.save(transaction);
        return true;
    }

    /**
     * Возвращает список всех транзакций пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Список транзакций.
     */
    public List<Transaction> getTransactions(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    /**
     * Редактирует существующую транзакцию.
     *
     * @param transactionId Идентификатор транзакции.
     * @param amount        Новая сумма транзакции.
     * @param categoryId    Новый идентификатор категории.
     * @param description   Новое описание транзакции.
     * @return {@code true}, если транзакция обновлена успешно.
     */
    public boolean editTransaction(Long transactionId, BigDecimal amount, Long categoryId, String description) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setAmount(amount);
        transaction.setCategoryId(categoryId);
        transaction.setDescription(description);

        transactionRepository.update(transaction);
        return true;
    }

    /**
     * Удаляет транзакцию.
     *
     * @param transactionId Идентификатор транзакции.
     * @return {@code true}, если транзакция удалена успешно.
     */
    public boolean deleteTransaction(Long transactionId) {
        transactionRepository.delete(transactionId);
        return true;
    }
}