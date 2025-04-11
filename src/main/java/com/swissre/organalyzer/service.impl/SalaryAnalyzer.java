package com.swissre.organalyzer.service.impl;

import com.swissre.organalyzer.model.Employee;
import com.swissre.organalyzer.model.SalaryViolation;
import com.swissre.organalyzer.service.Analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalaryAnalyzer implements Analyzer<SalaryViolation> {

    @Override
    public List<SalaryViolation> analyze(Map<String, Employee> employees) {
        List<SalaryViolation> violations = new ArrayList<>();
        for (Employee manager : employees.values()) {
            if (!manager.subordinates().isEmpty()) {
                double averageSalary = manager.subordinates().stream().mapToDouble(Employee::salary).average().orElse(0);
                double lowerThreshold = averageSalary * 1.2; // For 20% more than average salary
                double upperThreshold = averageSalary * 1.5; // For 50% more than average salary
                double salary = manager.salary();
                if (salary < lowerThreshold) {
                    violations.add(new SalaryViolation(manager.getFullName(), salary, lowerThreshold, true));
                } else if (salary > upperThreshold) {
                    violations.add(new SalaryViolation(manager.getFullName(), salary, upperThreshold, false));
                }
            }
        }
        return violations;
    }
}
