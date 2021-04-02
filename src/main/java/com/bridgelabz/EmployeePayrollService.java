package com.bridgelabz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
    enum IOService{ CONSOLE_IO, FILE_IO, DB_IO, REST_IO; }
    private List<EmployeePayrollServicedatebase> employeePayrollList;

    public EmployeePayrollService(List<EmployeePayrollServicedatebase> empList) {
        this.employeePayrollList = empList;
    }

    public static void main(String[] args) {
        ArrayList<EmployeePayrollServicedatebase> employeePayrollDataList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollDataList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
    }

    public void writeEmployeePayrollData(IOService ioService) {
        if(ioService.equals(IOService.CONSOLE_IO))
            System.out.println("EmployeePayrollServicedatabase to console:\n " + employeePayrollList);
        else if(ioService.equals(IOService.FILE_IO))
            new EmployeePayrollIO().writeData(employeePayrollList);
    }

    public void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Enter Id : ");
        int id = consoleInputReader.nextInt();
        consoleInputReader.nextLine();

        System.out.println("Enter name : ");
        String name = consoleInputReader.nextLine();

        System.out.println("Enter salary : ");
        double salary = consoleInputReader.nextDouble();
        consoleInputReader.nextLine();
        employeePayrollList.add(new EmployeePayrollServicedatebase(id, name, salary));
    }

    public void printData(IOService ioService) {
        if(ioService.equals(IOService.FILE_IO))
            new EmployeePayrollIO().printData();
    }

    public long countEntries(IOService ioService) {
        if(ioService.equals(IOService.FILE_IO))
            return new EmployeePayrollIO().countEntries();
                return 0;
    }
}