import java.io.*;
import java.util.*;

// Employee class with serialization support
class Employee implements Serializable {
    int id;
    String name;
    String department;
    double salary;

    // Constructor
    Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Display employee info in table row format (no special characters)
    void display() {
        System.out.printf("%-5d %-15s %-15s %-15s\n", id, name, department,
                String.format("%.2f", salary));
    }

    public String toString() {
        return id + ", " + name + ", " + department + ", " + salary;
    }
}

public class EmployeeSerializationCLI {

    static List<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File currentTextFile = null;
        File binaryFile = null;

        while (true) {
            System.out.println("\n========== Employee Management ==========");
            System.out.println("1. Load employees from text file");
            System.out.println("2. Serialize employees to .dat file");
            System.out.println("3. Deserialize from file and display");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    File currentDir = new File(".");
                    File[] txtFiles = currentDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

                    if (txtFiles == null || txtFiles.length == 0) {
                        System.out.println("No .txt files found in the current directory.");
                        break;
                    }

                    System.out.println("\nAvailable .txt files:");
                    for (int i = 0; i < txtFiles.length; i++) {
                        System.out.println((i + 1) + ". " + txtFiles[i].getName());
                    }

                    System.out.print("Enter the number of the file to load: ");
                    int fileChoice = -1;

                    try {
                        fileChoice = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid input. Please enter a number.");
                        break;
                    }

                    if (fileChoice < 1 || fileChoice > txtFiles.length) {
                        System.out.println("Invalid choice.");
                        break;
                    }

                    currentTextFile = txtFiles[fileChoice - 1];
                    employeeList = readEmployeesFromTextFile(currentTextFile);
                    System.out.println("Loaded " + employeeList.size() + " employees from file: " + currentTextFile.getName());
                    break;

                case 2:
                    if (employeeList.isEmpty()) {
                        System.out.println("No employee data loaded. Load from text file first.");
                        break;
                    }

                    String baseName = currentTextFile.getName().replaceFirst("\\.\\w+$", "");
                    binaryFile = new File(baseName + ".dat");

                    serializeEmployees(employeeList, binaryFile);
                    System.out.println("Employees serialized to file: " + binaryFile.getName());
                    break;

                case 3:
                    if (binaryFile == null || !binaryFile.exists()) {
                        System.out.println("Serialized file not found. Serialize data first.");
                        break;
                    }

                    List<Employee> deserializedList = deserializeEmployees(binaryFile);

                    System.out.println("\n======= Employee List =======");
                    System.out.printf("%-5s %-15s %-15s %-15s\n", "ID", "Name", "Department", "Salary");
                    System.out.println("========================================================");
                    for (Employee emp : deserializedList) {
                        emp.display();
                    }
                    System.out.println("Total Employees: " + deserializedList.size());
                    break;

                case 4:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    static List<Employee> readEmployeesFromTextFile(File file) {
        List<Employee> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNum = 0;

            while ((line = br.readLine()) != null) {
                lineNum++;
                String[] parts = line.split(",");

                if (lineNum == 1 && parts[0].toLowerCase().contains("id")) {
                    continue;
                }

                if (parts.length != 4) {
                    System.out.println("Skipping invalid line " + lineNum + ": " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String department = parts[2].trim();
                    double salary = Double.parseDouble(parts[3].trim());
                    list.add(new Employee(id, name, department, salary));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format on line " + lineNum + ": " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading text file: " + e.getMessage());
        }

        return list;
    }

    static void serializeEmployees(List<Employee> list, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
        }
    }

    static List<Employee> deserializeEmployees(File file) {
        List<Employee> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            list = (List<Employee>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
        }
        return list;
    }
}