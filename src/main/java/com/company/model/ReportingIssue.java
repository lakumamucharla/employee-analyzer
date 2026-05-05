package com.company.model;

public class ReportingIssue {

    private final String employeeName;
    private final int excessLevels;

    public ReportingIssue(String employeeName, int excessLevels) {
        this.employeeName = employeeName;
        this.excessLevels = excessLevels;
    }

    @Override
    public String toString() {
        return employeeName +
                " has reporting line longer by " +
                excessLevels + " levels";
    }
}