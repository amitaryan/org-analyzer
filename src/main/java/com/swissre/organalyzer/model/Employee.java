package com.swissre.organalyzer.model;

import java.util.List;

public record Employee(String id, String firstName, String lastName, double salary, String managerId, List<Employee> subordinates) {
    public String getFullName() { return firstName + " " + lastName; }
}
