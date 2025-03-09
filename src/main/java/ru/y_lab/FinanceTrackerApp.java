package ru.y_lab;

import ru.y_lab.menu.MainMenu;

import java.util.Scanner;

public class FinanceTrackerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainMenu.showMainMenu(scanner);
    }
}
