package ru.y_lab;

import ru.y_lab.menu.*;
import ru.y_lab.service.*;

import java.util.Scanner;

public class FinanceTrackerApp {
    public static void main(String[] args) {
        UserService userService = new UserService();
        TransactionService transactionService = new TransactionService();
        BudgetService budgetService = new BudgetService();
        GoalService goalService = new GoalService();
        AnalyticsService analyticsService = new AnalyticsService(transactionService);
        Scanner scanner = new Scanner(System.in);

        // Настройки для EmailService
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String emailUsername = "ваш_email@gmail.com";
        String emailPassword = "ваш_пароль";

        EmailService emailService = new EmailService(smtpHost, smtpPort, emailUsername, emailPassword);
        NotificationService notificationService = new NotificationService(budgetService, transactionService, emailService);

        TransactionMenu transactionMenu = new TransactionMenu(transactionService, scanner);
        ProfileMenu profileMenu = new ProfileMenu(userService, scanner);
        BudgetMenu budgetMenu = new BudgetMenu(budgetService, transactionService, scanner);
        GoalMenu goalMenu = new GoalMenu(goalService, scanner);
        AnalyticsMenu analyticsMenu = new AnalyticsMenu(analyticsService, scanner);
        NotificationMenu notificationMenu = new NotificationMenu(notificationService, goalService, scanner);
        AdminMenu adminMenu = new AdminMenu(userService, transactionService, scanner);
        UserMenu userMenu = new UserMenu(transactionService, transactionMenu, profileMenu, budgetMenu, goalMenu, analyticsMenu, notificationMenu, scanner);
        MainMenu mainMenu = new MainMenu(userService, userMenu, adminMenu, scanner);

        mainMenu.show();
    }
}
