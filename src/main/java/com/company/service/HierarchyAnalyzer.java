package com.company.service;

import com.company.model.*;

import java.util.*;

public class HierarchyAnalyzer {

    private final int maxAllowedDepth;

    public HierarchyAnalyzer() {
        this(4); // default
    }

    public HierarchyAnalyzer(int maxAllowedDepth) {
        this.maxAllowedDepth = maxAllowedDepth;
    }

    public List<ReportingIssue> analyze(Map<Integer, Employee> map) {

        List<ReportingIssue> issues = new ArrayList<>();

        for (Employee emp : map.values()) {

            int managersBetween = getManagersBetweenEmployeeAndCeo(emp, map);

            if (managersBetween > maxAllowedDepth) {
                issues.add(new ReportingIssue(
                        emp.getFullName(),
                        managersBetween - maxAllowedDepth));
            }
        }

        return issues;
    }

    private int getManagersBetweenEmployeeAndCeo(Employee emp, Map<Integer, Employee> map) {

        int managersBetween = 0;
        Integer managerId = emp.getManagerId();
        Set<Integer> visited = new HashSet<>();

        while (managerId != null) {
            if (!visited.add(managerId)) {
                break; // prevent infinite loop if malformed cycle exists
            }

            Employee manager = map.get(managerId);
            if (manager == null) {
                break;
            }

            // Exclude CEO from the "between employee and CEO" count.
            if (manager.getManagerId() != null) {
                managersBetween++;
            }

            managerId = manager.getManagerId();
        }

        return managersBetween;
    }
}