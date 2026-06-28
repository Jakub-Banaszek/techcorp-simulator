package com.example.techcorp.ui;

import com.example.techcorp.engine.GameEngine;
import java.util.Scanner;

public class ConsoleUI {
    private GameEngine engine;
    private Scanner scanner;

    
    public ConsoleUI(GameEngine engine) {
        this.engine = engine;
        this.scanner = new Scanner(System.in); 
    }

    
    public void start() {
        while (engine.isRunning()) {
            displayMenu();
            int choice = readChoice();
            handleChoice(choice);
        }
    }

    
    private void displayMenu() {
        System.out.println("\n=================================");
        System.out.println("TURA: " + engine.getTurn());
        System.out.println("=================================");
        
        engine.getCompany().showStatus();
        
        System.out.println("\nSTATUS PROJEKTÓW:");
        
        for (com.example.techcorp.domain.Project project : engine.getCompany().getProjects()) {
            System.out.println("- " + project.getName() + 
                               " | Postęp: " + project.getProgress() + " / " + project.getRequiredWork() +
                               " | Status: " + project.getStatus());
        }
        
        System.out.println("=================================");
        System.out.println("Wybierz akcję:");
        System.out.println("1. Przejdź do następnej tury (wykonaj pracę i zapłać pensje)");
        System.out.println("2. Zatrudnij nowego pracownika (losowo)");
        System.out.println("3. Zwolnij pracownika");                 
        System.out.println("4. Wstrzymaj lub wznów projekt");        
        System.out.print("0. Wyjdź z gry\nTwój wybór: ");
    }

    
    private int readChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("To nie jest poprawna liczba! Spróbuj ponownie.");
            scanner.next(); 
            System.out.print("Twój wybór: ");
        }
        return scanner.nextInt();
    }

    
    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                engine.nextTurn();
                break;
            case 2:
                engine.hireRandomEmployee();
                break;
            case 3:
                fireEmployeeMenu(); 
                break;
            case 4:
                toggleProjectMenu(); 
                break;
            case 0:
                engine.quit();
                break;
            default:
                System.out.println("Nieznana opcja. Wybierz 1, 2, 3, 4 lub 0.");
        }
    }

    
    private void fireEmployeeMenu() {
        java.util.List<com.example.techcorp.domain.Employee> emps = engine.getCompany().getEmployees();
        if (emps.isEmpty()) {
            System.out.println("Brak pracowników do zwolnienia.");
            return;
        }
        
        System.out.println("\n--- KOGO CHCESZ ZWOLNIĆ? ---");
        for (int i = 0; i < emps.size(); i++) {
            System.out.println(i + ". " + emps.get(i).getClass().getSimpleName() + " " + 
                               emps.get(i).getName() + " (Pensja: " + emps.get(i).getSalary() + ")");
        }
        System.out.print("Podaj numer pracownika (lub -1 aby anulować): ");
        int index = readChoice();
        
        if (index != -1) {
            engine.fireEmployee(index);
        }
    }

    
    private void toggleProjectMenu() {
        java.util.List<com.example.techcorp.domain.Project> projs = engine.getCompany().getProjects();
        if (projs.isEmpty()) {
            System.out.println("Brak projektów.");
            return;
        }
        
        System.out.println("\n--- KTÓRY PROJEKT WSTRZYMAĆ/WZNOWIĆ? ---");
        for (int i = 0; i < projs.size(); i++) {
            System.out.println(i + ". " + projs.get(i).getName() + " [Aktualny status: " + projs.get(i).getStatus() + "]");
        }
        System.out.print("Podaj numer projektu (lub -1 aby anulować): ");
        int index = readChoice();
        
        if (index != -1) {
            engine.toggleProjectStatus(index);
        }
    }
}