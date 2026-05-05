package com.company.service;

import com.company.model.IssueType;
import com.company.model.SalaryIssue;
import com.company.model.ReportingIssue;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator {

    private static final String SEP = "=".repeat(60);
    private static final String SUB = "-".repeat(60);
    private static final int MAX_DEPTH = 4;

    public String generateReport(List<SalaryIssue> salaryIssues,
                                 List<ReportingIssue> reportingIssues) {
        List<SalaryIssue> safeSalaryIssues =
                salaryIssues == null ? Collections.emptyList() : salaryIssues;
        List<ReportingIssue> safeReportingIssues =
                reportingIssues == null ? Collections.emptyList() : reportingIssues;

        StringBuilder sb = new StringBuilder();

        appendHeader(sb);
        appendSalarySection(sb, safeSalaryIssues);
        appendReportingSection(sb, safeReportingIssues);
        appendSummary(sb, safeSalaryIssues, safeReportingIssues);

        return sb.toString();
    }

    // ================= HEADER =================
    private void appendHeader(StringBuilder sb) {
        sb.append("\n").append(SEP).append("\n");
        sb.append("   ORGANIZATION STRUCTURE ANALYSIS REPORT\n");
        sb.append(SEP).append("\n");
    }

    // ================= SALARY =================
    private void appendSalarySection(StringBuilder sb, List<SalaryIssue> issues) {

        sb.append("\nSALARY ANALYSIS\n").append(SUB).append("\n");

        List<SalaryIssue> underpaid = issues.stream()
                .filter(i -> IssueType.UNDERPAID.equals(i.getType()))
                .collect(Collectors.toList());

        List<SalaryIssue> overpaid = issues.stream()
                .filter(i -> IssueType.OVERPAID.equals(i.getType()))
                .collect(Collectors.toList());

        // UNDERPAID
        sb.append("\nManagers earning LESS than expected:\n");
        if (underpaid.isEmpty()) {
            sb.append("  None found.\n");
        } else {
            for (SalaryIssue issue : underpaid) {
                sb.append(formatSalary(issue));
            }
        }

        // OVERPAID
        sb.append("\nManagers earning MORE than expected:\n");
        if (overpaid.isEmpty()) {
            sb.append("  None found.\n");
        } else {
            for (SalaryIssue issue : overpaid) {
                sb.append(formatSalary(issue));
            }
        }
    }

    private String formatSalary(SalaryIssue issue) {
        return "  - " + issue + "\n";
    }

    // ================= REPORTING =================
    private void appendReportingSection(StringBuilder sb,
                                        List<ReportingIssue> issues) {

        sb.append("\nREPORTING LINE ANALYSIS\n").append(SUB).append("\n");

        if (issues == null || issues.isEmpty()) {
            sb.append("No employees exceed max depth (").append(MAX_DEPTH).append(").\n");
            return;
        }

        sb.append("\nEmployees with reporting line exceeding ")
                .append(MAX_DEPTH).append(" levels:\n");

        for (ReportingIssue issue : issues) {
            sb.append(formatDepth(issue));
        }
    }

    private String formatDepth(ReportingIssue issue) {
        return "  - " + issue + "\n";
    }

    // ================= SUMMARY =================
    private void appendSummary(StringBuilder sb,
                               List<SalaryIssue> salaryIssues,
                               List<ReportingIssue> reportingIssues) {

        long underpaid = salaryIssues.stream()
                .filter(i -> IssueType.UNDERPAID.equals(i.getType()))
                .count();

        long overpaid = salaryIssues.stream()
                .filter(i -> IssueType.OVERPAID.equals(i.getType()))
                .count();
        int reportingCount = reportingIssues.size();

        sb.append("\nSUMMARY\n").append(SUB).append("\n");

        sb.append(String.format("Underpaid Managers : %d%n", underpaid));
        sb.append(String.format("Overpaid Managers  : %d%n", overpaid));
        sb.append(String.format("Long Reporting     : %d%n", reportingCount));

        sb.append(SUB).append("\n");

        sb.append(String.format("Total Issues       : %d%n",
                underpaid + overpaid + reportingCount));
    }
}