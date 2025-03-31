package org.example.datamodel;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idEmployee;
    private String name;
    private int workDuration;

    public Employee(int idEmployee, String name) {
        this.idEmployee = idEmployee;
        this.name = name;
    }
    public Employee(int idEmployee) {
        this.idEmployee = idEmployee;
    }
    public Employee(int idEmployee, String name, int workDuration) {
        this.idEmployee = idEmployee;
        this.name = name;
        this.workDuration = workDuration;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public String getName() {
        return name;
    }

    public int getWorkDuration() {
        return workDuration;
    }
    public void setWorkDuration(int workDuration) {
        this.workDuration = workDuration;
    }
}

