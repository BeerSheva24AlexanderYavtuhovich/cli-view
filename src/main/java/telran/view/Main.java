package telran.view;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

record Employee(long id, String name, String department, int salary, LocalDate birthDate) {
}

public class Main {
    static InputOutput io = new StandardInputOutput();
    final static int MIN_SALARY = 5000;
    final static int MAX_SALARY = 30000;
    final static String[] DEPARTMENTS = { "QA", "Audit", "Development", "Management" };
    final static long MIN_ID = 100000;
    final static long MAX_ID = 999999;
    final static int MIN_AGE = 18;
    final static int MAX_AGE = 70;

    public static void main(String[] args) {
        readEmployeeBySeparateFields();
    }

    static void readEmployeeAsObject() {
        Employee empl = io.readObject("Enter employee data in the format:" +
                " <id>#<name>#<department>#<salary>#<yyyy-MM-DD> ",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new Employee(Long.parseLong(tokens[0]), tokens[1], tokens[2],
                            Integer.parseInt(tokens[3]), LocalDate.parse(tokens[4]));

                });
        io.writeLine("You are entered the following Employee data");
        io.writeLine(empl);
    }

    static void readEmployeeBySeparateFields() {
        long id = (io.readNumberRange("Enter employee ID (between " + MIN_ID + " and " + MAX_ID + "):",
                "Invalid ID. Must be a number between " + MIN_ID + " and " + MAX_ID + ".", MIN_ID, MAX_ID)).longValue();

        String name = io.readStringPredicate("Enter employee name (at least 3 letters, first capitalized):",
                "Invalid name format.",
                n -> n.matches("[A-Z][a-z]{2,}"));

        String department = io.readStringOptions("Enter department (" + String.join(", ", DEPARTMENTS) + "):",
                "Invalid department.",
                new HashSet<>(Arrays.asList(DEPARTMENTS)));

        int salary = (io.readNumberRange("Enter employee salary (between " + MIN_SALARY + " and " + MAX_SALARY + "):",
                "Invalid salary.", MIN_SALARY, MAX_SALARY)).intValue();

        LocalDate birthdate = io.readIsoDateRange(
                "Enter birthdate in format yyyy-MM-DD. Age must be in the range from " + MIN_AGE + " to " + MAX_AGE,
                "Invalid date format.", LocalDate.now().minusYears(MAX_AGE), LocalDate.now().minusYears(MIN_AGE));

        Employee empl = new Employee(id, name, department, salary, birthdate);
        io.writeLine("You have entered the following Employee data:");
        io.writeLine(empl);
    }
}