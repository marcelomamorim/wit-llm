/*
 * @test
 * @summary Test PrintStream(String fileName) constructor for basic functionality and exception
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileName();
        testInvalidFileName();
    }

    private static void testValidFileName() throws Exception {
        File tempFile = File.createTempFile("PrintStreamTest", ".txt");
        tempFile.deleteOnExit();

        PrintStream ps = new PrintStream(tempFile.getPath());
        ps.println("Hello, world!");
        ps.close();

        if (ps.checkError()) {
            throw new RuntimeException("PrintStream reported error after writing to valid file");
        }

        if (!tempFile.exists() || tempFile.length() == 0) {
            throw new RuntimeException("File was not created or is empty after PrintStream write");
        }
    }

    private static void testInvalidFileName() {
        String invalidFileName = "/this/path/should/not/exist/hopefully/PrintStreamTest.txt";
        try {
            new PrintStream(invalidFileName);
            throw new RuntimeException("Expected FileNotFoundException was not thrown for invalid file name");
        } catch (FileNotFoundException e) {
            // Expected exception
        }
    }
}
