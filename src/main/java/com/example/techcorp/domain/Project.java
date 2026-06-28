package com.example.techcorp.domain;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private int requiredWork;
    private int progress;
    private List<Employee> team = new ArrayList<>();
    private ProjectStatus status; 

    public Project(String name, int requiredWork) {
        this.name = name;
        this.requiredWork = requiredWork;
        this.progress = 0;
        this.team = new ArrayList<>();
        this.status = ProjectStatus.PLANNED;
    }

    public void addWorker(Employee worker) {
        if (worker == null) {
            throw new IllegalArgumentException("Worker cannot be null.");
        }
        team.add(worker);
    }

    public void removeWorker(Employee worker) {
        team.remove(worker);
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public void start() {
        if (status == ProjectStatus.PLANNED) {
            status = ProjectStatus.IN_PROGRESS;
        }
    }

    public void cancel() {
        if (status != ProjectStatus.FINISHED) {
            status = ProjectStatus.CANCELLED;
        }
    }

    public void workOneTurn() {
        if (status != ProjectStatus.IN_PROGRESS) {
            return;
        }

        for (Employee worker : team) {
            progress += worker.work();
        }

        if (progress >= requiredWork) {
            progress = requiredWork;
            status = ProjectStatus.FINISHED;
        }
    }

    public boolean isFinished() { 
        return status == ProjectStatus.FINISHED; 
    }

    public String getName() { return name; }
    public int getRequiredWork() { return requiredWork; }
    public int getProgress() { return progress; }
    
    public List<Employee> getTeam() { return team; }
    public ProjectStatus getStatus() { return status; }
}