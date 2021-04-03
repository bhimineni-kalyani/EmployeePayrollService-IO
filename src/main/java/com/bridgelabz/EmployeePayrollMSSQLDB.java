package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePayrollMSSQLDB {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
    private static final String userName = "Root";
    private static final String passWord = "R00t@1";

    private static EmployeePayrollMSSQLDB employeePayrollDBService;
    private PreparedStatement employeePayrollDataStatement;

    private EmployeePayrollMSSQLDB() {

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
        System.out.println("connection is successful : " + connection);
        return connection;
    }

    public List<EmployeePayrollServicedatebase> readData() {
        String selectQuery = "SELECT * from employee_payroll";
        return this.getEmployeePayrollDataForGivenSql(selectQuery);
    }

    public int updateEmployeeData(String name, double salary) {
        String sqlUpdate = String.format("salary = %.2f WHERE name = '%s';", salary, name);
        try(Connection connection = this.getConnection()){
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
        try{
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollList;
    }

    private List<EmployeePayrollServicedatebase> getEmployeePayrollData(ResultSet resultSet){
        List<EmployeePayrollServicedatebase> employeePayrollDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollServicedatebase(id, name, salary, startDate));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataList;
    }

    private List<EmployeePayrollServicedatebase> getEmployeePayrollDataForGivenSql(String sql){
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return getEmployeePayrollData(resultSet);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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

    public int updateEmployeeDataPreparedStatement(String name, double salary) {
        try(Connection connection = this.getConnection()){
            String sql = "UPDATE employee_payroll SET salary = ? WHERE name = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, salary);
            preparedStatement.setString(2, name);
            int resultSet = preparedStatement.executeUpdate();
            return resultSet;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public EmployeePayrollServicedatebase addEmployeeToPayroll(String name, double salary, LocalDate startDate, String gender) {
        int Id = -1;
        EmployeePayrollServicedatebase employeePayrollData = null;
        String sql = String.format("INSERT INTO employee_payroll (name, gender, salary, start)VALUES " +
                "('%s', '%s', %s, '%s')", name, gender, salary, Date.valueOf(startDate));
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if(rowAffected == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) Id = resultSet.getInt(1);
            }
            employeePayrollData = new EmployeePayrollServicedatebase(Id, name, salary, startDate);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollData;
    }

    public List<EmployeePayrollServicedatebase> getEmployeePayrollDataBetweenDates(String from, String to) {
        Date start = Date.valueOf(from);
        Date end = (to == null) ? Date.valueOf(LocalDate.now()) : Date.valueOf(to);
        String sql = String.format("SELECT * FROM employee_payroll WHERE start BETWEEN '%s' AND '%s'",
                start, end);
        return this.getEmployeePayrollDataForGivenSql(sql);
    }

    public List<String> calculateSumAverageMinMax() {
        List<String> outputFromDB = new ArrayList<>();
        try(Connection connection = this.getConnection()) {
            String sql = "SELECT Sum(salary), Avg(salary), Min(salary), Max(salary) FROM employee_payroll";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                outputFromDB.add(Double.toString(resultSet.getDouble("Sum(salary)")));
                outputFromDB.add(Double.toString(resultSet.getDouble("Avg(salary)")));
                outputFromDB.add(Double.toString(resultSet.getDouble("Min(salary)")));
                outputFromDB.add(Double.toString(resultSet.getDouble("Max(salary)")));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return outputFromDB;
    }

    public Map<String, List<Double>> calculateSumAverageMinMax_GroupByGender() {
        Map<String, List<Double>> outputMap = new HashMap<>();
        try(Connection connection = this.getConnection()) {
            String sql = "SELECT gender, SUM(salary), AVG(salary), MIN(salary), MAX(salary) " + "FROM employee_payroll GROUP BY gender";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                String gender = resultSet.getString("Gender");
                List<Double> fieldList = new ArrayList<>();
                fieldList.add(resultSet.getDouble("SUM(salary)"));
                fieldList.add(resultSet.getDouble("AVG(salary)"));
                fieldList.add(resultSet.getDouble("MIN(salary)"));
                fieldList.add(resultSet.getDouble("MAX(salary)"));
                outputMap.put(gender, fieldList);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return outputMap;
    }
}