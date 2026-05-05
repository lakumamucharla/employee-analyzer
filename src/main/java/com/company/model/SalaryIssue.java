package com.company.model;

public class SalaryIssue {

    private final String managerName;
    private final double deviation;
    private final IssueType type;

    public SalaryIssue(String managerName, double deviation, IssueType type) {
        this.managerName = managerName;
        this.deviation = deviation;
        this.type = type;
    }

    public IssueType getType() {
        return type;
    }

    @Override
    public String toString() {
        return managerName + " earns " + type +
                " by " + String.format("%.2f", deviation);
    }
}