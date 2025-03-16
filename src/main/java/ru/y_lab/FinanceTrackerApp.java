package ru.y_lab;

import ru.y_lab.menu.*;
import ru.y_lab.repository.*;
import ru.y_lab.service.*;

import java.util.Scanner;

public class FinanceTrackerApp {
    public static void main(String[] args) {
        // Инициализация репозиториев
        UserRepository userRepository = new UserRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        BudgetRepository budgetRepository = new BudgetRepository();
        GoalRepository goalRepository = new GoalRepository();
        CategoryRepository categoryRepository = new CategoryRepository();

        // Инициализация сервисов
        UserService userService = new UserService(userRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, categoryRepository);
        BudgetService budgetService = new BudgetService(budgetRepository);
        GoalService goalService = new GoalService(goalRepository);
        AnalyticsService analyticsService = new AnalyticsService(transactionService);

        // Настройки для EmailService
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String emailUsername = "ваш_email@gmail.com";
        String emailPassword = "ваш_пароль";

        EmailService emailService = new EmailService(smtpHost, smtpPort, emailUsername, emailPassword);
        NotificationService notificationService = new NotificationService(budgetService, transactionService, goalService, emailService);

        // Инициализация меню
        Scanner scanner = new Scanner(System.in);
        TransactionMenu transactionMenu = new TransactionMenu(transactionService, scanner);
        ProfileMenu profileMenu = new ProfileMenu(userService, scanner);
        BudgetMenu budgetMenu = new BudgetMenu(budgetService, transactionService, scanner);
        GoalMenu goalMenu = new GoalMenu(goalService, scanner);
        AnalyticsMenu analyticsMenu = new AnalyticsMenu(analyticsService, scanner);
        NotificationMenu notificationMenu = new NotificationMenu(notificationService, goalService, scanner);
        AdminMenu adminMenu = new AdminMenu(userService, transactionService, scanner);
        UserMenu userMenu = new UserMenu(transactionService, transactionMenu, profileMenu, budgetMenu, goalMenu, analyticsMenu, notificationMenu, scanner);
        MainMenu mainMenu = new MainMenu(userService, userMenu, adminMenu, scanner);

        // Запуск приложения
        mainMenu.show();
    }
}
