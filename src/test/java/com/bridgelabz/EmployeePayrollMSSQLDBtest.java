package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static com.bridgelabz.EmployeePayrollService.IOService.DB_IO;

public class EmployeePayrollMSSQLDBtest {
    private Assertions Assert;

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollServicedatebase> EmployeePayrollData = EmployeePayrollService.readData(DB_IO);
        Assert.assertEquals(3, EmployeePayrollServicedatebase.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDatabase() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollServicedatebase> employeePayrollDataList = employeePayrollService.readData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Sri", 3000000);
        boolean isSync = employeePayrollService.checkEmployeePayrollInSyncWithDB("Sri");
        Assert.assertTrue(isSync);
    }
}