package com.company.service;

import com.company.model.Employee;
import com.company.util.CsvReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeIntegrationTest {

    private Map<Integer, Employee> load(String fileName) {
       // URL resource = getClass().getClassLoader()
              //  .getResource("test-data/" + fileName);


        String path = "src/test/resources/test-data/"+fileName;
        //assertNotNull(resource, "Test file not found: " + path);
        //Path filePath = Path.of(path);
        Map<Integer, Employee> map = CsvReader.read(path);
        new EmployeeService().buildHierarchy(map);
        return map;
    }

    @Test
    @DisplayName("Should handle empty file (only header)")
    void testEmptyFile() {
        Map<Integer, Employee> map = load("empty-file.csv");
        assertTrue(map.isEmpty());
    }

    @Test
    @DisplayName("Should ignore duplicate IDs")
    void testDuplicateIds() {
        Map<Integer, Employee> map = load("duplicate-id.csv");
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("Should skip invalid column rows")
    void testInvalidColumns() {
        Map<Integer, Employee> map = load("invalid-columns.csv");
        assertTrue(map.isEmpty());
    }

    @Test
    @DisplayName("Should handle missing manager gracefully")
    void testMissingManager() {
        Map<Integer, Employee> map = load("missing-manager.csv");
        assertNotNull(map);
    }

    @Test
    @DisplayName("Should detect underpaid.csv manager")
    void testUnderpaid() {
        Map<Integer, Employee> map = load("underpaid.csv");
        assertFalse(new SalaryAnalyzer().analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should detect overpaid.csv manager")
    void testOverpaid() {
        Map<Integer, Employee> map = load("overpaid.csv");
        assertFalse(new SalaryAnalyzer().analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should return no issues for valid data")
    void testNoIssues() {
        Map<Integer, Employee> map = load("no-issues.csv");
        assertTrue(new SalaryAnalyzer().analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should detect when more than 4 managers are between employee and CEO")
    void testLongHierarchy() {
        Map<Integer, Employee> map = load("long-hierarchy.csv");

        HierarchyAnalyzer ha = new HierarchyAnalyzer(4);

        assertFalse(ha.analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should not flag when exactly four managers are between employee and CEO")
    void testHierarchyBoundaryExactlyFourBetween() {
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee(1, "CEO", "Root", 100000, null));
        map.put(2, new Employee(2, "M1", "One", 90000, 1));
        map.put(3, new Employee(3, "M2", "Two", 80000, 2));
        map.put(4, new Employee(4, "M3", "Three", 70000, 3));
        map.put(5, new Employee(5, "M4", "Four", 60000, 4));
        map.put(6, new Employee(6, "IC", "Exact", 50000, 5));

        assertTrue(new HierarchyAnalyzer(4).analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should flag when more than four managers are between employee and CEO")
    void testHierarchyBoundaryMoreThanFourBetween() {
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee(1, "CEO", "Root", 100000, null));
        map.put(2, new Employee(2, "M1", "One", 90000, 1));
        map.put(3, new Employee(3, "M2", "Two", 80000, 2));
        map.put(4, new Employee(4, "M3", "Three", 70000, 3));
        map.put(5, new Employee(5, "M4", "Four", 60000, 4));
        map.put(6, new Employee(6, "M5", "Five", 55000, 5));
        map.put(7, new Employee(7, "IC", "Over", 50000, 6));

        assertEquals(1, new HierarchyAnalyzer(4).analyze(map).size());
    }

    @Test
    @DisplayName("Should handle single CEO")
    void testSingleCEO() {
        Map<Integer, Employee> map = load("single-ceo.csv");

        assertTrue(new SalaryAnalyzer().analyze(map).isEmpty());
        assertTrue(new HierarchyAnalyzer().analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should handle zero salary")
    void testZeroSalary() {
        Map<Integer, Employee> map = load("zero-salary.csv");
        assertTrue(new SalaryAnalyzer().analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should handle large dataset")
    void testLargeDataset() {
        Map<Integer, Employee> map = load("large-dataset.csv");

        assertNotNull(map);
        assertTrue(map.size() >= 10);
    }

    @Test
    @DisplayName("Should not flag manager at exact +20% lower boundary")
    void testSalaryExactLowerBoundary() {
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee(1, "Manager", "LowBound", 120, null));
        map.put(2, new Employee(2, "Emp", "A", 100, 1));
        map.put(3, new Employee(3, "Emp", "B", 100, 1));
        new EmployeeService().buildHierarchy(map);

        assertTrue(new SalaryAnalyzer().analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should not flag manager at exact +50% upper boundary")
    void testSalaryExactUpperBoundary() {
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee(1, "Manager", "HighBound", 150, null));
        map.put(2, new Employee(2, "Emp", "A", 100, 1));
        map.put(3, new Employee(3, "Emp", "B", 100, 1));
        new EmployeeService().buildHierarchy(map);

        assertTrue(new SalaryAnalyzer().analyze(map).isEmpty());
    }

    @Test
    @DisplayName("Should report multiple salary violations in same dataset")
    void testMultipleSalaryViolations() {
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee(1, "CEO", "Root", 200, null));
        map.put(2, new Employee(2, "Under", "Mgr", 100, 1));
        map.put(3, new Employee(3, "Over", "Mgr", 200, 1));
        map.put(4, new Employee(4, "Emp", "A", 100, 2));
        map.put(5, new Employee(5, "Emp", "B", 100, 2));
        map.put(6, new Employee(6, "Emp", "C", 100, 3));
        map.put(7, new Employee(7, "Emp", "D", 100, 3));
        new EmployeeService().buildHierarchy(map);

        assertEquals(2, new SalaryAnalyzer().analyze(map).size());
    }

    @Test
    @DisplayName("Should handle cyclic manager links without infinite loop")
    void testHierarchyCycleSafety() {
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee(1, "A", "One", 100, 2));
        map.put(2, new Employee(2, "B", "Two", 100, 1));

        assertDoesNotThrow(() -> new HierarchyAnalyzer(4).analyze(map));
    }
}