package com.company.util;

import com.company.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class CsvReader {

    public static Map<Integer, Employee> read(String filePath) {

        Map<Integer, Employee> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            br.readLine(); // skip header

            String line;

            while ((line = br.readLine()) != null) {

                String[] p = line.split(",");

                if (p.length < 4) continue;

                try {
                    int id = Integer.parseInt(p[0]);
                    double salary = Double.parseDouble(p[3]);

                    if (salary < 0 || map.containsKey(id)) continue;

                    Integer managerId = (p.length < 5 || p[4].isEmpty())
                            ? null
                            : Integer.parseInt(p[4]);

                    map.put(id, new Employee(
                            id, p[1], p[2], salary, managerId));

                } catch (Exception ignored) {}
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading file", e);
        }

        return map;
    }
}