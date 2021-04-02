package com.bridgelabz;

public class EmployeePayrollServicedatebase {
    int id;
    String name;
    double salary;

    public EmployeePayrollServicedatebase(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Id : " +this.id+ ",Name : " +this.name+ ",Salary : " +this.salary;
    }
}