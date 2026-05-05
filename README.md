# Employee Analyzer

## ▶️ Quick Start (Run with Program Arguments)

Pass the input CSV file path as the first program argument.

### IntelliJ IDEA

1. Open `Application.java`.
2. Go to **Run -> Edit Configurations...**.
3. In **Program arguments**, provide:

```
src/main/resources/employees.csv
```

4. Run `Application`.

### Maven (CLI)

From the project root:

```
mvn clean compile exec:java -Dexec.mainClass="com.company.main.Application" -Dexec.args="src/main/resources/employees.csv"
```

## 📌 Overview

This project analyzes an organization's employee structure based on a CSV input file.
It identifies salary inconsistencies among managers and detects employees with excessively long reporting hierarchies.

The goal is to demonstrate clean code practices, proper object-oriented design, and efficient data processing using Java.

---

## 🚀 Features

### 1. Salary Analysis

* Identifies managers earning **less than expected**
* Identifies managers earning **more than expected**
* Based on subordinate average salary:

    * Minimum expected = **20% above average**
    * Maximum expected = **50% above average**

---

### 2. Reporting Line Analysis

* Detects employees with **reporting depth greater than allowed**
* Default max depth: **4 levels**
* Displays:

    * Actual depth
    * Allowed depth
    * Excess levels

---

### 3. Structured Console Report

* Clean and readable output
* Section-wise breakdown:

    * Salary Analysis
    * Reporting Line Analysis
    * Summary

---

## 🧱 Project Structure

```
src/main/java/com/company
│
├── model
│   ├── Employee.java
│   ├── SalaryIssue.java
│   ├── ReportingIssue.java
│
├── service
│   ├── SalaryAnalyzer.java
│   ├── HierarchyAnalyzer.java
│   ├── ReportGenerator.java
│
├── util
│   ├── CsvReader.java
│
└── main
    ├── Application.java
```

---

## 📂 Input Format (CSV)

```
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300
```

---

## ▶️ How to Run

1. Clone the repository:

```
git clone https://github.com/<your-username>/employee-analyzer.git
```

2. Open the project in IntelliJ (or any Java IDE with Maven support).

3. Run `Application.java` with the CSV file path as the first program argument:

```
src/main/resources/employees.csv
```

---

## 📊 Sample Output

```
============================================================
   ORGANIZATION STRUCTURE ANALYSIS REPORT
============================================================

SALARY ANALYSIS
------------------------------------------------------------

Managers earning LESS than expected:
  - Uma Underpaid earns UNDERPAID by 20.00

Managers earning MORE than expected:
  - Owen Overpaid earns OVERPAID by 50.00

REPORTING LINE ANALYSIS
------------------------------------------------------------

Employees with reporting line exceeding 4 levels:
  - Iris Node13 has reporting line longer by 1 levels

SUMMARY
------------------------------------------------------------
Underpaid Managers : 1
Overpaid Managers  : 1
Long Reporting     : 1
------------------------------------------------------------
Total Issues       : 3
```

---

## 🧪 Test Coverage

Current test suite includes **23 automated test cases**:

* `EmployeeIntegrationTest` (16 tests)
  * CSV/data validation: empty file, invalid rows, duplicate IDs, missing manager
  * Salary analysis: underpaid, overpaid, valid/no-issue datasets
  * Hierarchy analysis: long hierarchy, exact boundary, > boundary, cycle safety
  * Additional scenarios: single CEO, zero salary, large dataset
* `SalaryAnalyzerTest` (2 tests)
  * Unit-level validation for underpaid detection and valid manager salary
* `HierarchyAnalyzerTest` (1 test)
  * Unit-level validation for long reporting chain detection
* `ReportGeneratorTest` (2 tests)
  * Report rendering with all issue types
  * Null/empty input handling and summary totals
* `ApplicationTest` (2 tests)
  * Program argument validation (missing CSV path)
  * End-to-end execution path with valid CSV argument

### Run Tests

```bash
mvn test
```

### Optional Coverage Report (JaCoCo)

```bash
mvn clean test jacoco:report
```

Generated HTML report:

```
target/site/jacoco/index.html
```

---

## ⚙️ Tech Stack

* Java 17
* Java 8 Streams
* Maven
* IntelliJ IDEA

---

## 🧠 Design Highlights

* Separation of concerns (Reader → Analyzer → Report)
* Stream-based processing for clean logic
* Immutable-style issue models
* Readable and maintainable code structure

---

## 📎 Notes

* Input file is expected as a program argument.
* Example default file location:

```
src/main/resources/employees.csv
```

## ✅ Assumptions

* Reporting-line rule uses the assignment wording literally: managers counted are those **between** employee and CEO (CEO is excluded).
* Rows with invalid data (bad numbers, negative salary, malformed columns) are ignored.
* Duplicate employee IDs are ignored after the first valid occurrence.
* If a manager ID is missing in the dataset, hierarchy traversal stops at the last known manager.
* Cyclic manager relationships are treated as malformed input and traversal is stopped defensively.

---

## 👤 Author

Lakuma Prakash
Java Developer (5+ years experience)

---

## ✅ Status

✔ Completed
✔ Tested
✔ Ready for review/interview
