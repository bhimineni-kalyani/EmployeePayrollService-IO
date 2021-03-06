package com.bridgelabz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeePayrollIO {
    public static String PAYROLL_FILE_NAME = "payrollFile.txt";

    public void writeData(List<EmployeePayrollServicedatebase> employeePayrollDataList) {
        StringBuffer empBuffer = new StringBuffer();
        employeePayrollDataList.forEach(employee -> {
            String employeeDataString = employee.toString().concat("\n");
            empBuffer.append(employeeDataString);
        });
        try {
            Files.write(Paths.get(PAYROLL_FILE_NAME), empBuffer.toString().getBytes());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printData() {
        try {
            Files.lines(new File(PAYROLL_FILE_NAME).toPath())
                    .forEach(System.out::println);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long countEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath())
                    .count();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public List<EmployeePayrollServicedatebase> readData() {
        try {
            return Files.lines(new File(PAYROLL_FILE_NAME).toPath())
                    .map(line -> line.trim())
                    .map(line -> EmployeePayrollServicedatebase.extractEmployeePayrollServicedatabaseObject(line))
                    .collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}