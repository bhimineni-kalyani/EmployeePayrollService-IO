package com.bridgelabz;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
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
    public void givenNewSalaryForEmployee_WhenUpdated_UsingStatement_ShouldSyncWithDatabase(){
        List<EmployeePayrollServicedatebase> employeePayrollDataList = employeePayrollService.readData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Sri", 3000000, 1);
        boolean isSync = employeePayrollService.checkEmployeePayrollInSyncWithDB("Sri");
        Assert.assertTrue(isSync);
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_UsingPreparedStatement_ShouldSyncWithDatabase() {
        List<EmployeePayrollServicedatebase> employeePayrollDataList = employeePayrollService.readData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Sri", 2000000, 1);
        boolean isSync = employeePayrollService.checkEmployeePayrollInSyncWithDB("Sri");
        Assert.assertTrue(isSync);
    }

    @Test
    public void givenDatesInRange_WhenRetrievedDataBetweenTwoDates_ShouldMatchEmployeeCount() {
        String from = "2018-01-01";
        String to = null;
        List<EmployeePayrollServicedatebase> employeePayrollDataList = employeePayrollService.getEmployeePayrollDataBetweenDates(DB_IO, from, to);
        Assert.assertEquals(3, employeePayrollDataList.size());
    }
}