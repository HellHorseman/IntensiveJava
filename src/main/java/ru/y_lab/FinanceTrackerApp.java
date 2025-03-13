package ru.y_lab;

import ru.y_lab.menu.*;
import ru.y_lab.service.*;

public class FinanceTrackerApp {
    public static void main(String[] args) {
        UserService userService = new UserService();
        TransactionService transactionService = new TransactionService();
        BudgetService budgetService = new BudgetService();
        GoalService goalService = new GoalService();
        AnalyticsService analyticsService = new AnalyticsService(transactionService);

        // Настройки для EmailService
        String smtpHost = "smtp.gmail.com"; // SMTP-сервер (например, для Gmail)
        String smtpPort = "587"; // Порт для TLS
        String emailUsername = "ваш_email@gmail.com"; // Ваш email
        String emailPassword = "ваш_пароль"; // Ваш пароль от email

        EmailService emailService = new EmailService(smtpHost, smtpPort, emailUsername, emailPassword);
        NotificationService notificationService = new NotificationService(budgetService, transactionService, emailService);

        TransactionMenu transactionMenu = new TransactionMenu(transactionService);
        ProfileMenu profileMenu = new ProfileMenu(userService);
        BudgetMenu budgetMenu = new BudgetMenu(budgetService, transactionService);
        GoalMenu goalMenu = new GoalMenu(goalService);
        AnalyticsMenu analyticsMenu = new AnalyticsMenu(analyticsService);
        NotificationMenu notificationMenu = new NotificationMenu(notificationService, goalService);
        UserMenu userMenu = new UserMenu(transactionService, transactionMenu, profileMenu, budgetMenu, goalMenu, analyticsMenu, notificationMenu);
        MainMenu mainMenu = new MainMenu(userService, userMenu, profileMenu, budgetMenu);

        mainMenu.show();
    }
}
