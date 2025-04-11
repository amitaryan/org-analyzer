package com.swissre.organalyzer;


import com.swissre.organalyzer.context.AnalyzerContext;
import com.swissre.organalyzer.data.CsvDataReader;
import com.swissre.organalyzer.model.Employee;
import com.swissre.organalyzer.model.HierarchyViolation;
import com.swissre.organalyzer.model.SalaryViolation;
import com.swissre.organalyzer.service.impl.HierarchyAnalyzer;
import com.swissre.organalyzer.service.impl.SalaryAnalyzer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzerServiceTest {

    private static Map<String, Employee> employees;

    @BeforeAll
    static void setup() throws Exception {
        employees = CsvDataReader.getInstance().read("employees-test.csv");
        assertNotNull(employees);
        assertEquals(20, employees.size());
    }

    @Test
    void testSalaryViolationsAreReturned() {
        AnalyzerContext<SalaryViolation> salaryContext = new AnalyzerContext<>(new SalaryAnalyzer());
        List<SalaryViolation> violations = salaryContext.execute(employees);

        assertNotNull(violations);
        assertTrue(violations.size() == 5, "There should be salary violations");
    }

    @Test
    void testUnderpaidManagersFilterWorks() {
        AnalyzerContext<SalaryViolation> salaryContext = new AnalyzerContext<>(new SalaryAnalyzer());
        List<SalaryViolation> violations = salaryContext.execute(employees);

        List<SalaryViolation> underpaid = violations.stream()
                .filter(SalaryViolation::isUnderpaid).toList();

        assertTrue(underpaid.size() == 4, "There should be underpaid managers");
    }

    @Test
    void testOverpaidManagersFilterWorks() {
        AnalyzerContext<SalaryViolation> salaryContext = new AnalyzerContext<>(new SalaryAnalyzer());
        List<SalaryViolation> violations = salaryContext.execute(employees);

        List<SalaryViolation> overpaid = violations.stream()
                .filter(v -> !v.isUnderpaid()).toList();

        assertTrue(overpaid.size() == 1, "There should be overpaid managers");
    }

    @Test
    void testHierarchyViolationsAreReturned() {
        AnalyzerContext<HierarchyViolation> hierarchyContext = new AnalyzerContext<>(new HierarchyAnalyzer());
        List<HierarchyViolation> violations = hierarchyContext.execute(employees);

        assertNotNull(violations);
        assertTrue(violations.stream().anyMatch(v -> v.depth() > 4), "Should include at least one deep employee");
    }

}

