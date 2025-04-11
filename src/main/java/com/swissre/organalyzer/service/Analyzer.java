package com.swissre.organalyzer.service;

import com.swissre.organalyzer.model.Employee;

import java.util.List;
import java.util.Map;

public interface Analyzer<T> {
    List<T> analyze(Map<String, Employee> employees);
}
