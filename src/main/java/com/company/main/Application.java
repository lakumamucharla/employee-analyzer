package com.company.main;

import com.company.model.*;
import com.company.service.*;
import com.company.util.CsvReader;

import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Provide CSV file path");
            return;
        }

        Map<Integer, Employee> map = CsvReader.read(args[0]);

        new EmployeeService().buildHierarchy(map);

        SalaryAnalyzer sa = new SalaryAnalyzer();
        HierarchyAnalyzer ha = new HierarchyAnalyzer();
        ReportGenerator rg = new ReportGenerator();

        List<SalaryIssue> salaryIssues = sa.analyze(map);
        List<ReportingIssue> reportingIssues = ha.analyze(map);

        String report = rg.generateReport(salaryIssues, reportingIssues);
        System.out.println(report);
    }
}