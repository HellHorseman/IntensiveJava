package ru.y_lab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionServiceTest {
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    void testAddTransaction() {
        assertTrue(transactionService.addTransaction("test@example.com", 100.0, "Food", "2023-10-01", "Lunch", "expense"));
    }

    @Test
    void testGetTransactions() {
        transactionService.addTransaction("test@example.com", 100.0, "Food", "2023-10-01", "Lunch", "expense");
        assertEquals(1, transactionService.getTransactions("test@example.com").size());
    }

    @Test
    void testEditTransaction() {
        transactionService.addTransaction("test@example.com", 100.0, "Food", "2023-10-01", "Lunch", "expense");
        String transactionId = transactionService.getTransactions("test@example.com").get(0).getId();
        assertTrue(transactionService.editTransaction(transactionId, 150.0, "Food", "Dinner"));
    }

    @Test
    void testDeleteTransaction() {
        transactionService.addTransaction("test@example.com", 100.0, "Food", "2023-10-01", "Lunch", "expense");
        String transactionId = transactionService.getTransactions("test@example.com").get(0).getId();
        assertTrue(transactionService.deleteTransaction(transactionId));
    }
}
