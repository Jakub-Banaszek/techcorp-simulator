package com.example.techcorp.engine;

import com.example.techcorp.domain.Company;
import com.example.techcorp.domain.Project;
import com.example.techcorp.domain.ProjectStatus;
import com.example.techcorp.domain.Developer;
import com.example.techcorp.domain.Employee;
import com.example.techcorp.domain.Intern;
import com.example.techcorp.domain.Manager;
import com.example.techcorp.domain.Tester;
import com.example.techcorp.events.GameEvent;
import com.example.techcorp.events.MarketSlowdownEvent;
import com.example.techcorp.events.InvestorBonusEvent;
import java.util.Random;

public class GameEngine {
    private Company company;
    private int turn;
    private boolean running;

    public GameEngine() {
        this.company = new Company("TechCorp", 500000);
        this.turn = 1;
        this.running = true;
        setupInitialState();
    }

    private void setupInitialState() {
        Employee anna = new Developer("Anna", 9, 8000);
        Employee piotr = new Tester("Piotr", 6, 6500);
        Employee ewa = new Manager("Ewa", 7, 9000);
        Employee tomek = new Intern("Tomek", 4, 3000);

        company.hire(anna);
        company.hire(piotr);
        company.hire(ewa);
        company.hire(tomek);

        Project mobileApp = new Project("Mobile App", 100);
        mobileApp.addWorker(anna);
        mobileApp.addWorker(piotr);
        mobileApp.addWorker(ewa);
        mobileApp.addWorker(tomek);

        company.startProject(mobileApp);
        mobileApp.start();

        Project website = new Project("Website", 20); 
        website.addWorker(piotr);                     
        company.startProject(website);                
        website.start();                              
    }

    public void nextTurn() {
        if (!running) {
            return;
        }

        System.out.println("\n--- ZAKOŃCZENIE TURY " + turn + " ---");
        
        boolean allFinished = true;
        for (Project project : company.getProjects()) {
            project.workOneTurn();
            if (!project.isFinished()) {
                allFinished = false; 
            }
        }
        
        company.paySalaries();
        System.out.println("Wypłacono pensje. Aktualny stan konta: " + company.getCash());

        Random random = new Random();
        int chance = random.nextInt(100);
        if (chance < 15) {
            GameEvent badEvent = new MarketSlowdownEvent();
            badEvent.apply(company);
        } else if (chance >= 85) {
            GameEvent goodEvent = new InvestorBonusEvent();
            goodEvent.apply(company);
        }

        if (company.getCash() < 0) {
            System.out.println("\n[GAME OVER] BANKRUCTWO! Firma " + company.getName() + " zbankrutowała.");
            running = false;
            return;
        }

        if (allFinished && !company.getProjects().isEmpty()) {
            System.out.println("\n[SUKCES] Wszystkie projekty firmy zostały pomyślnie ukończone!");
            running = false;
        } else {
            turn++;
        }
    }

    public void hireRandomEmployee() {
        Random random = new Random();
        int type = random.nextInt(3);
        int skill = random.nextInt(6) + 3;
        double salary = skill * 1000;
        
        Employee newHired;
        String[] names = {"Marek", "Kasia", "Janek", "Ola", "Tomek"};
        String name = names[random.nextInt(names.length)] + " " + (random.nextInt(90) + 10);

        if (type == 0) {
            newHired = new Developer(name, skill, salary);
        } else if (type == 1) {
            newHired = new Tester(name, skill, salary);
        } else {
            newHired = new Manager(name, skill, salary);
        }

        if (company.getCash() < salary) {
            System.out.println(" Nie stać Cię na zatrudnienie tego pracownika! Wymagana pensja: " + salary);
            return;
        }

        company.hire(newHired);
        
        for (Project project : company.getProjects()) {
            if (!project.isFinished()) {
                project.addWorker(newHired);
                System.out.println(" Zatrudniono: " + newHired.getRoleName() + " " + newHired.getName() + 
                                   " i przypisano do projektu: " + project.getName());
                return;
            }
        }
        System.out.println(" Zatrudniono: " + newHired.getRoleName() + " " + newHired.getName() + " (brak aktywnych projektów).");
    }

    public void fireEmployee(int index) {
        if (index >= 0 && index < company.getEmployees().size()) {
            Employee fired = company.getEmployees().get(index);
            
            company.getEmployees().remove(fired);
            
            for (Project p : company.getProjects()) {
                p.removeWorker(fired);
            }
            
            System.out.println(" Zwolniono pracownika: " + fired.getName() + " (Budżet odciążony o " + fired.getSalary() + ")");
        } else {
            System.out.println(" Nieprawidłowy numer pracownika.");
        }
    }

    public void toggleProjectStatus(int index) {
        if (index >= 0 && index < company.getProjects().size()) {
            Project p = company.getProjects().get(index);
            
            if (p.getStatus() == ProjectStatus.FINISHED) {
                System.out.println(" Ten projekt jest już zakończony!");
            } else if (p.getStatus() == ProjectStatus.ON_HOLD) {
                p.setStatus(ProjectStatus.IN_PROGRESS);
                System.out.println(" Wznowiono projekt: " + p.getName());
            } else {
                p.setStatus(ProjectStatus.ON_HOLD);
                System.out.println(" Wstrzymano projekt: " + p.getName() + " (Nie będzie generował postępu)");
            }
        } else {
            System.out.println(" Nieprawidłowy numer projektu.");
        }
    }

    public void quit() {
        System.out.println("Dziękujemy za grę w TechCorp Simulator!");
        this.running = false;
    }

    public Company getCompany() { return company; }
    public int getTurn() { return turn; }
    public boolean isRunning() { return running; }
}