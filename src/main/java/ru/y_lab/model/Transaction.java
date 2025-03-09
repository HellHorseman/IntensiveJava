package ru.y_lab.model;

import java.time.LocalDate;

public class Transaction {
    private final int id;
    private final int userId;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    public Transaction(int id, int userId, double amount, String category, LocalDate date, String description) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Сумма: " + amount +
                ", Категория: " + category +
                ", Дата: " + date +
                ", Описание: " + description;
    }
}
