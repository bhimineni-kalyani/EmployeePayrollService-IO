package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollMSSQLDB {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
    private static final String userName = "Root";
    private static final String passWord = "R00t@1";

    private static EmployeePayrollMSSQLDB employeePayrollDBService;
    private PreparedStatement employeePayrollDataStatement;

    EmployeePayrollMSSQLDB() {

    }

    public static EmployeePayrollMSSQLDB getInstance() {
        if(employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollMSSQLDB();
        return employeePayrollDBService;
    }

    private Connection getConnection() throws SQLException {
        Connection connection;
        System.out.println("Database : " + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, passWord);
        System.out.println("Connection is successful : " + connection);
        return connection;
    }

    public List<EmployeePayrollServicedatebase> readData() {
        String selectQuery = "SELECT * from employee_payroll";
        List<EmployeePayrollServicedatebase> employeePayrollList = new ArrayList<>();
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
                employeePayrollList = this.getEmployeePayrollServicedatebase(resultSet);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollList;
    }

    public int updateEmployeeData(String name, double salary) {
        String sqlUpdate = String.format("salary = %.2f WHERE name = '%s';", salary, name);
        try(Connection connection = this.getConnection()) {
                Statement statement = connection.createStatement();
                return statement.executeUpdate(sqlUpdate);
        }
        catch (SQLException throwables) {
                throwables.printStackTrace();
        }
        return 0;
    }

    public List<EmployeePayrollServicedatebase> getEmployeePayrollData(String name) {
        List<EmployeePayrollServicedatebase> employeePayrollList = null;
        if(this.employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollServicedatebase(resultSet);
        }
        catch (SQLException throwables) {
                throwables.printStackTrace();
        }
        return employeePayrollList;
    }

    private List<EmployeePayrollServicedatebase> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollServicedatebase> employeePayrollDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    employeePayrollDataList.add(new EmployeePayrollServicedatebase(id, name, salary, date));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataList;
    }

    public void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_payroll WHERE name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}