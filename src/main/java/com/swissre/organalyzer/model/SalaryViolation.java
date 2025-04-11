package com.swissre.organalyzer.model;

public record SalaryViolation(String employeeName, double salary, double expected, boolean isUnderpaid) {
    @Override
    public String toString() {
        return String.format("%s earns %.2f %s than expected (%.2f vs %.2f)",
                employeeName,
                Math.abs(salary - expected),
                isUnderpaid ? "LESS" : "MORE",
                salary,
                expected);
    }
}
