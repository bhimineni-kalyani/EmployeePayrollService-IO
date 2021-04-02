package com.bridgelabz;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeePayrollServicedatebase {
    int id;
    String name;
    double salary;
    LocalDate date;

    public EmployeePayrollServicedatebase(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public EmployeePayrollServicedatebase(int id, String name, double salary, LocalDate date) {
        this(id, name, salary);
        this.date = date;
    }

    public static EmployeePayrollServicedatebase extractEmployeePayrollServicedatabaseObject(String line) {
        String[] fields = line.split(",");
        String[] storageFields = new String[fields.length];
        Pattern pattern = Pattern.compile("(?<=([:][\\s]))[0-9a-zA-Z.\\s]+");
        int index = 0;
        for(String field : fields) {
            Matcher matcher = pattern.matcher(field);
            if(matcher.find())
                storageFields[index++] = matcher.group();
        }
        int id = Integer.parseInt(storageFields[0]);
        String name = storageFields[1];
        double salary = Double.parseDouble(storageFields[2]);
        return new EmployeePayrollServicedatebase(id, name, salary);
    }

    @Override
    public String toString() {
        return "Emp id: " + this.id + ", Name: " + this.name + ", Salary: " + this.salary;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        EmployeePayrollServicedatebase that = (EmployeePayrollServicedatebase) o;
        return this.id == that.id && Double.compare(this.salary, that.salary) == 0 && this.name.equals(that.name);
    }
}