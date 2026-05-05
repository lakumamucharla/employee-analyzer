package com.company.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final double salary;
    private final Integer managerId;
    private final List<Employee> subordinates = new ArrayList<>();

    public Employee(int id, String firstName, String lastName, double salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public int getId() { return id; }
    public String getFullName() { return firstName + " " + lastName; }
    public double getSalary() { return salary; }
    public Integer getManagerId() { return managerId; }
    public List<Employee> getSubordinates() { return subordinates; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee that = (Employee) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}