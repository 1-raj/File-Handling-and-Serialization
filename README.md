# 💼 Employee Management System (Java CLI Project)

## 📌 Project Introduction

This is a **console-based Java application** developed as a major project to demonstrate **file handling and object serialization**. The program allows users to load employee data from a `.txt` file, serialize the data into a `.dat` binary file, and later deserialize and display the records in a structured table format.

It follows a **menu-driven structure** for user interaction and is fully compatible with any standard command-line terminal (CMD, PowerShell, or Linux shell). No special characters or external libraries are used, making it portable and easy to run.

---

## 🚀 Features

- Lists all `.txt` files available in the current folder for selection
- Loads employee data from a selected `.txt` file
- Validates and parses comma-separated values into objects
- Serializes employee data into a `.dat` file using Java's `ObjectOutputStream`
- Deserializes employee data and displays it in a table format
- Simple and user-friendly command-line interface (CLI)
- No GUI or special character dependency (fully terminal-safe)

---

## 📁 Folder Structure

EmployeeSerializationCLI.java → Main Java source code
employees.txt → Sample input text file (comma-separated)
employees.dat → Serialized binary file (generated at runtime)
README.md → Project documentation


---

## 🧾 Input File Format

Input file must be a plain text `.txt` file in CSV format:

```text
id,name,department,salary
1,Raj Sharma,IT,55000
2,Aman Patel,HR,48000
3,Neha Verma,Finance,62000
...
The first line is treated as a header and is automatically skipped.
All fields are comma-separated and properly trimmed.
Invalid lines or bad data formats are ignored with a warning.

## 🖥️ How to Run

### 🔹 Step 1: Compile the program

```bash
javac EmployeeSerializationCLI.java
java EmployeeSerializationCLI

## Menu on running
========== Employee Management ==========
1. Load employees from text file
2. Serialize employees to .dat file
3. Deserialize from file and display
4. Exit

## Output display (Deserialization display)
======= Employee List =======
ID    Name            Department      Salary
1     Raj Sharma      IT              55000.00
2     Aman Patel      HR              48000.00
3     Neha Verma      Finance         62000.00
Total Employees: 3
