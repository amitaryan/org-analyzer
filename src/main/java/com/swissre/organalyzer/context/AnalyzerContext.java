package com.swissre.organalyzer.context;

import com.swissre.organalyzer.model.Employee;
import com.swissre.organalyzer.service.Analyzer;

import java.util.List;
import java.util.Map;

public class AnalyzerContext<T> {
    private final Analyzer<T> analyzer;

    public AnalyzerContext(Analyzer<T> analyzer) {
        this.analyzer = analyzer;
    }

    public List<T> execute(Map<String, Employee> employees) {
       return analyzer.analyze(employees);
    }
}
