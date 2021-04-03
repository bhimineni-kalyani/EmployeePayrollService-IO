package com.bridgelabz;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static com.bridgelabz.EmployeePayrollService.IOService.DB_IO;

public class EmployeePayrollMSSQLDBtest {
    private EmployeePayrollService employeePayrollService;
    private Assertions Assert;

    @Before
    public void init() {
        employeePayrollService = new EmployeePayrollService();
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount(){
        List<EmployeePayrollServicedatebase> employeePayrollData = employeePayrollService.readData(DB_IO);
        Assert.assertEquals(3, employeePayrollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_UsingStatement_ShouldSyncWithDatabase() {
        List<EmployeePayrollServicedatebase> employeePayrollDataList = employeePayrollService.readData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Bill", 3000000, 1);
        boolean isSync = employeePayrollService.checkEmployeePayrollInSyncWithDB("Bill");
        Assert.assertTrue(isSync);
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_UsingPreparedStatement_ShouldSyncWithDatabase() {
        List<EmployeePayrollServicedatebase> employeePayrollDataList = employeePayrollService.readData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Charlie", 2000000, 1);
        boolean isSync = employeePayrollService.checkEmployeePayrollInSyncWithDB("Charlie");
        Assert.assertTrue(isSync);
    }

    @Test
    public void givenDatesInRange_WhenRetrievedDataBetweenTwoDates_ShouldMatchEmployeeCount() {
        String from = "2018-01-01";
        String to = null;
        List<EmployeePayrollServicedatebase> employeePayrollDataList = employeePayrollService.getEmployeePayrollDataBetweenDates(DB_IO, from, to);
        Assert.assertEquals(3, employeePayrollDataList.size());
    }

    @Test
    public void givenEmployeePayrollInDB_WhenCalculated_Sum_Min_Max_Average_ofSalary_GroupByGender_ShouldGiveCorrectOutput(){
        Map<String, List<Double>> outputMapFromDB = employeePayrollService.calculateSumAverageMinMax_GroupByGender(DB_IO);
        List<Double> expectedMaleList = Arrays.asList( 30000.00, 15000.00, 10000.00, 20000.00);
        List<Double> expectedFemaleList = Arrays.asList(30000.00, 60000.00, 90000.00, 12000.00);
        Assert.assertTrue(outputMapFromDB.get("M").equals(expectedMaleList) && outputMapFromDB.get("F").equals(expectedFemaleList));
    }
}