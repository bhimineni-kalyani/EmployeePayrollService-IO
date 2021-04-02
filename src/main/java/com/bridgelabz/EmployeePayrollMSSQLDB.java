package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollMSSQLDB {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
    private static final String userName = "Root";
    private static final String passWord = "R00t@1";

    private Connection getConnection() throws SQLException {
        Connection connection;
        System.out.println("Connecting to Servicedatabase : " +jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, passWord);
        System.out.println("Connection is successful : " +connection);
        return connection;
    }

    public List<EmployeePayrollServicedatebase> readData() {
        String selectQuery = "SELECT * from employee_payroll";
        List<EmployeePayrollServicedatebase> employeePayrollList = new ArrayList<>();
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                employeePayrollList.add(new EmployeePayrollServicedatebase(id, name, salary, date));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollList;
    }
}