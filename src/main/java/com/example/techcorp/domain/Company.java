package com.example.techcorp.domain;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private double cash;
    private List<Employee> employees;
    private List<Project> projects;

    public Company(String name, double cash) {
        this.name = name;
        this.cash = cash;
        this.employees = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public void hire(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        employees.add(employee);
    }

    public void startProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null.");
        }
        projects.add(project);
    }

    
    public void paySalaries() {
        double totalSalaries = 0;
        for (Employee employee : employees) {
            totalSalaries += employee.getSalary();
        }
        this.cash -= totalSalaries; 
    }

    public void showStatus() {
        System.out.println("=== COMPANY STATUS ===");
        System.out.println("Name: " + name);
        System.out.println("Cash: " + cash);
        System.out.println("Employees: " + employees.size());
        System.out.println("Projects: " + projects.size());
    }

    public void reduceCash(double amount) {
        this.cash -= amount;
    }

    public void addCash(double amount) {
        this.cash += amount;
    }

    public String getName() { return name; }
    public double getCash() { return cash; }
    public List<Employee> getEmployees() { return employees; }
    public List<Project> getProjects() { return projects; }
}