package com.swissre.organalyzer.service.impl;

import com.swissre.organalyzer.model.Employee;
import com.swissre.organalyzer.model.HierarchyViolation;
import com.swissre.organalyzer.service.Analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HierarchyAnalyzer implements Analyzer<HierarchyViolation> {
    @Override
    public List<HierarchyViolation> analyze(Map<String, Employee> employees) {
        List<HierarchyViolation> violations = new ArrayList<>();
        for (Employee e : employees.values()) {
            int depth = getDepth(e, employees);
            if (depth > 4) {
                violations.add(new HierarchyViolation(e.getFullName(), depth));
            }
        }
        return violations;
    }

    private int getDepth(Employee e, Map<String, Employee> map) {
        int count = 0;
        while (e.managerId() != null) {
            e = map.get(e.managerId());
            count++;
        }
        return count;
    }
}
