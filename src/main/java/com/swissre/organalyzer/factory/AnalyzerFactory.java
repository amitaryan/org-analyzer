package com.swissre.organalyzer.factory;

import com.swissre.organalyzer.service.Analyzer;
import com.swissre.organalyzer.service.impl.HierarchyAnalyzer;
import com.swissre.organalyzer.service.impl.SalaryAnalyzer;

public class AnalyzerFactory {
    public static Analyzer<?> getAnalyzer(String type) {
        return switch (type.toLowerCase()) {
            case "salary" -> new SalaryAnalyzer();
            case "hierarchy" -> new HierarchyAnalyzer();
            default -> throw new IllegalArgumentException("Unknown analyzer type: " + type);
        };
    }
}
