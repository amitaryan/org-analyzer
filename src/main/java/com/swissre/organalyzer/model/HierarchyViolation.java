package com.swissre.organalyzer.model;

public record HierarchyViolation (String employeeName, int depth){
    @Override
    public String toString() {
        return String.format("%s has %d levels of management (too long by %d)", employeeName, depth, depth - 4);
    }
}
