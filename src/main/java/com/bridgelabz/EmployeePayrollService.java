package com.bridgelabz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
    private final List<EmployeePayrollServicedatebase> employeePayrollList;

    public EmployeePayrollService(List<EmployeePayrollServicedatebase> empList) {
        this.employeePayrollList = empList;
    }

    public static void main(String[] args) {
        ArrayList<EmployeePayrollServicedatebase> employeePayrollDataList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollDataList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollServicedatabase(consoleInputReader);
        employeePayrollService.writeEmployeePayrollServicedatabase();
    }

    private void writeEmployeePayrollServicedatabase() {
        System.out.println("EmployeePayrollServicedatabase to console:\n " + employeePayrollList);
    }

    private void readEmployeePayrollServicedatabase(Scanner consoleInputReader) {
        System.out.println("Enter Id : ");
        int id = consoleInputReader.nextInt();
        consoleInputReader.nextLine();

        System.out.println("Enter name : ");
        String name = consoleInputReader.nextLine();

        System.out.println("Enter salary: ");
        double salary = consoleInputReader.nextDouble();
        consoleInputReader.nextLine();
        employeePayrollList.add(new EmployeePayrollServicedatebase(id, name, salary));
    }
}