package com.company.service;

import com.company.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalaryAnalyzer {

    public List<SalaryIssue> analyze(Map<Integer, Employee> map) {

        List<SalaryIssue> issues = new ArrayList<>();

        for (Employee manager : map.values()) {

            if (manager.getSubordinates().isEmpty()) continue;

            double avg = manager.getSubordinates().stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0);

            if (avg == 0) continue;

            double min = avg * 1.2;
            double max = avg * 1.5;

            if (manager.getSalary() < min) {
                issues.add(new SalaryIssue(
                        manager.getFullName(),
                        min - manager.getSalary(),
                        IssueType.UNDERPAID));
            }

            if (manager.getSalary() > max) {
                issues.add(new SalaryIssue(
                        manager.getFullName(),
                        manager.getSalary() - max,
                        IssueType.OVERPAID));
            }
        }

        return issues;
    }
}