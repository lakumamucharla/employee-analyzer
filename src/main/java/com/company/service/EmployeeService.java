package com.company.service;

import com.company.model.Employee;

import java.util.Map;

public class EmployeeService {

    public void buildHierarchy(Map<Integer, Employee> map) {

        for (Employee emp : map.values()) {

            if (emp.getManagerId() != null) {

                Employee manager = map.get(emp.getManagerId());

                if (manager != null) {
                    manager.getSubordinates().add(emp);
                }
            }
        }
    }
}