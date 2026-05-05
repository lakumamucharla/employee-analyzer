# Employee Analyzer

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

1. Clone the repository

```
git clone https://github.com/<your-username>/employee-analyzer.git
```

2. Open in IntelliJ

3. Run:

```
Application.java
```

---

## 📊 Sample Output

```
============================================================
   ORGANIZATION STRUCTURE ANALYSIS REPORT
============================================================

SALARY ANALYSIS
------------------------------------------------------------

Managers UNDERPAID:
- Martin Chekov (ID: 124)
  Current Salary       : $45,000.00
  Subordinate Average : $50,000.00
  Expected Salary     : $60,000.00
  Underpaid By        : $15,000.00

Managers OVERPAID:
None found.

REPORTING LINE ANALYSIS
------------------------------------------------------------

No employees exceed max depth (4).

SUMMARY
------------------------------------------------------------
Underpaid Managers : 1
Overpaid Managers  : 0
Long Reporting     : 0
------------------------------------------------------------
Total Issues       : 1
```

---

## 🧪 Test Coverage

Includes:

* Edge cases (empty file, invalid data, duplicate IDs)
* Large dataset scenarios
* Long reporting hierarchy validation
* Salary boundary validations

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

* Input file should be placed in:

```
src/main/resources/employees.csv
```

## ✅ Assumptions

* Reporting-line rule uses the assignment wording literally: managers counted are those **between** employee and CEO (CEO is excluded).
* Rows with invalid data (bad numbers, negative salary, malformed columns) are ignored.
* Duplicate employee IDs are ignored after the first valid occurrence.
* If a manager ID is missing in the dataset, hierarchy traversal stops at the last known manager.
* Cyclic manager relationships are treated as malformed input and traversal is stopped defensively.


<<<<<<< Updated upstream

---

=======
>>>>>>> Stashed changes
## 👤 Author

Lakuma Prakash
Java Developer (5+ years experience)
<<<<<<< Updated upstream

---

## ✅ Status

✔ Completed
✔ Tested
✔ Ready for review/interview
=======
>>>>>>> Stashed changes
