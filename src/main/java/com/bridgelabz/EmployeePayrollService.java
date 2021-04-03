package com.bridgelabz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayrollService {
    enum IOService { CONSOLE_IO,
        FILE_IO,
        DB_IO,
        REST_IO
    }

    private List<EmployeePayrollServicedatebase> employeePayrollList;
    private final EmployeePayrollMSSQLDB employeePayrollDBService;

    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollMSSQLDB.getInstance();
    }

    public EmployeePayrollService(List<EmployeePayrollServicedatebase> empList) {
        this();
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
        System.out.println("Enter id : ");
        int id = consoleInputReader.nextInt();
        consoleInputReader.nextLine();

        System.out.println("Enter name : ");
        String name = consoleInputReader.nextLine();

        System.out.println("Enter salary: ");
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

    public ArrayList<EmployeePayrollServicedatebase> readData(IOService ioService) {
        if(ioService.equals(IOService.FILE_IO)){
            employeePayrollList = new EmployeePayrollIO().readData();
            return new ArrayList<>(employeePayrollList);
        }
        if(ioService.equals(IOService.DB_IO)){
            employeePayrollList = employeePayrollDBService.readData();
            return new ArrayList<>(employeePayrollList);
        }
        return null;
    }

    public void updateEmployeeSalary(String name, double salary, int choice) {
        int result = (choice == 1) ? employeePayrollDBService.updateEmployeeData(name, salary)
                : employeePayrollDBService.updateEmployeeDataPreparedStatement(name, salary);
        if(result == 0) return;
        EmployeePayrollServicedatebase employeePayrollData = this.getEmployeePayrollDataBetweenDates(name);
        if(employeePayrollData != null) employeePayrollData.salary = salary;
    }

    public void addEmployeeToPayroll(String name, double salary, LocalDate startDate, String gender) {
        employeePayrollList.add(employeePayrollDBService.addEmployeeToPayroll(name, salary, startDate, gender));
    }

    private EmployeePayrollServicedatebase getEmployeePayrollDataBetweenDates(String name) {
        return this.employeePayrollList.stream()
                    .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
                    .findFirst()
                    .orElse(null);
    }

    public List<EmployeePayrollServicedatebase> getEmployeePayrollDataBetweenDates(IOService ioService, String from, String to){
        if(ioService.equals(IOService.DB_IO)) {
            return employeePayrollDBService.getEmployeePayrollDataBetweenDates(from, to);
        }
        return null;
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<EmployeePayrollServicedatebase> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollDataBetweenDates(name));
    }

    public List<String> calculateSumAverageMinMax(IOService ioService) {
        if(ioService.equals(IOService.DB_IO)){
            return employeePayrollDBService.calculateSumAverageMinMax();
        }
        return null;
    }

    public Map<String, List<Double>> calculateSumAverageMinMax_GroupByGender(IOService ioService) {
        if(ioService.equals(IOService.DB_IO)) {
            return employeePayrollDBService.calculateSumAverageMinMax_GroupByGender();
        }
        return null;
    }
}