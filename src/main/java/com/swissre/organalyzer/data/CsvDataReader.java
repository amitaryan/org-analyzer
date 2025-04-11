package com.swissre.organalyzer.data;

import com.swissre.organalyzer.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CsvDataReader {
    private static CsvDataReader instance;

    private CsvDataReader() {}

    public static CsvDataReader getInstance() {
        if (instance == null) {
            instance = new CsvDataReader();
        }
        return instance;
    }

    public Map<String, Employee> read(String resourceName) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found in resources: " + resourceName);
        }
        var map = new HashMap<String, Employee>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            br.readLine(); // header
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",");
                Employee e = new Employee(t[0], t[1], t[2], Double.parseDouble(t[3]), t.length > 4 ? t[4] : null, new ArrayList<>());
                map.put(e.id(), e);
            }
        }

        // Link subordinates
        for (Employee e : map.values()) {
            if (e.managerId() != null) {
                Employee manager = map.get(e.managerId());
                if (manager != null) manager.subordinates().add(e);
            }
        }
        return map;
    }
}
