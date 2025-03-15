package ru.y_lab.model;

import java.time.LocalDate;

public class Transaction {
    private String id;
    private String userId;
    private double amount;
    private String category;
    private String date;
    private String description;
    private String type; // "income" or "expense"

    public Transaction(String id, String userId, double amount, String category, String date, String description, String type) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
