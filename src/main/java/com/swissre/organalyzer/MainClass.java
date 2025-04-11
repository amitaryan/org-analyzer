package com.swissre.organalyzer;


import com.swissre.organalyzer.context.AnalyzerContext;
import com.swissre.organalyzer.data.CsvDataReader;
import com.swissre.organalyzer.factory.AnalyzerFactory;
import com.swissre.organalyzer.model.HierarchyViolation;
import com.swissre.organalyzer.model.SalaryViolation;

import java.util.List;

/**
 * Example usage of the program
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        var employees = CsvDataReader.getInstance().read("employees.csv");

        AnalyzerContext<SalaryViolation> salaryCheck = new AnalyzerContext(AnalyzerFactory.getAnalyzer("salary"));
        var salaryViolations = salaryCheck.execute(employees);
        System.out.println("Manager's Salary Violations where they are earning less:");
        // Filter the data for employees who are learning less and print
        var underpaidManagers = salaryViolations.stream()
                .filter(SalaryViolation::isUnderpaid)
                .toList();
        System.out.println("Total overpaid Managers: " + underpaidManagers.size());
        underpaidManagers.forEach(System.out::println);

        System.out.println("\n\nManager's Salary Violations where they are earning more:");
        // Filter the data for employees who are learning less and print
        var overpaidManagers = salaryViolations.stream()
                .filter(v -> !v.isUnderpaid())
                .toList();
        System.out.println("Total underpaid Managers: "+overpaidManagers.size());
        overpaidManagers.forEach(System.out::println);
        System.out.println("\n\nHierarchy Violations");

        AnalyzerContext<HierarchyViolation> hierarchyCheck = new AnalyzerContext(AnalyzerFactory.getAnalyzer("hierarchy"));
        List<HierarchyViolation> hierarchyViolations = hierarchyCheck.execute(employees);
        System.out.println("Total Hierarchy Violations: " + hierarchyViolations.size());
        hierarchyViolations.forEach(System.out::println);
    }
}

