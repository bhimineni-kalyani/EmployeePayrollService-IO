package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class FileExistTest {
    private static final String Home = System.getProperty("user.home");
    private static final String PlaywithNIO = "PlayGround";
    private Assertions Assert;

    @Test
    public void givenPathWhenCheckedThenConfirm() throws IOException {
        Path homePath = Paths.get(Home);
        Assert.assertTrue(Files.exists(homePath));

        Path playPath = Paths.get(Home + "/" + PlaywithNIO);
        if(Files.exists(playPath)) FileExist.deleteFiles(playPath.toFile());
        Assert.assertTrue(Files.notExists(playPath));

        Files.createDirectory(playPath);
        Assert.assertTrue(Files.exists(playPath));

        IntStream.range(1, 10).forEach(cntr -> {
            Path tempFile = Paths.get(playPath + "/temp" + cntr);
            Assert.assertTrue(Files.notExists(tempFile));
            try {
                Files.createFile(tempFile);
            }
            catch(IOException e) {
            }
            Assert.assertTrue(Files.exists(tempFile));
        });

        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
        Files.newDirectoryStream(playPath).forEach(System.out::println);
        Files.newDirectoryStream(playPath, path -> path.toFile().isFile()
                && path.toString().startsWith("temp")).forEach(System.out::println);
    }

    @Test
    public void givenADirectoryWhenWatchedListsAllTheActivities() throws IOException {
        Path dir = Paths.get(Home +"/"+ PlaywithNIO);
        Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
        new WatchServicetowatch(dir).processEvents();
    }

    @Test
    public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries(){
        EmployeePayrollServicedatebase[] arrayOfEmps = {
                new EmployeePayrollServicedatebase(1, "Sri", 100000.0),
                new EmployeePayrollServicedatebase(2, "Sai", 150000.0),
                new EmployeePayrollServicedatebase(3, "Ram", 20000.0)
        };
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
        employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        Assert.assertEquals(3, entries);
    }

    @Test
    public void givenEmloyeePayrollFile_AfterReading_ShouldProcessTheDataAsAnArrayListOfEmployeePayrollObjects() {
        ArrayList<EmployeePayrollServicedatebase> employeePayrollDataArrayList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollDataArrayList);
        employeePayrollDataArrayList = employeePayrollService.readData(EmployeePayrollService.IOService.FILE_IO);
        for(EmployeePayrollServicedatebase emp : employeePayrollDataArrayList)
            System.out.println(emp);
        Assert.assertEquals(3, employeePayrollDataArrayList.size());
    }
}