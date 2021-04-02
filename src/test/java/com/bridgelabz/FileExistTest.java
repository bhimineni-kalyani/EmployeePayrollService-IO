package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class FileExistTest {
    private static String Home = System.getProperty("user.home");
    private static String PlaywithNIO = "PlayGround";
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
            catch(IOException e){
            }
            Assert.assertTrue(Files.exists(tempFile));
        });

        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
        Files.newDirectoryStream(playPath).forEach(System.out::println);
        Files.newDirectoryStream(playPath, path -> path.toFile().isFile()
                && path.toString().startsWith("temp")).forEach(System.out::println);
    }
}