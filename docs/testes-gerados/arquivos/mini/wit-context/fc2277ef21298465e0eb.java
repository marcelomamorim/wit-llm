/*
 * @test
 * @summary Test PrintStream(String fileName) constructor for basic functionality and error handling
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileName();
        testInvalidFileName();
    }

    private static void testValidFileName() throws Exception {
        String fileName = "PrintStreamWitupTestOutput.txt";
        PrintStream ps = null;
        try {
            ps = new PrintStream(fileName);
            ps.println("Test line");
            ps.flush();
        } finally {
            if (ps != null) {
                ps.close();
            }
            // Cleanup
            File f = new File(fileName);
            if (f.exists()) {
                f.delete();
            }
        }
    }

    private static void testInvalidFileName() {
        // Try to open a PrintStream with an invalid file name to trigger FileNotFoundException
        // Using a file name with invalid characters or a directory that does not exist
        String invalidFileName = "/this/path/should/not/exist/PrintStreamWitupTestOutput.txt";
        try {
            new PrintStream(invalidFileName);
            throw new RuntimeException("Expected FileNotFoundException was not thrown");
        } catch (FileNotFoundException e) {
            // Expected exception
        }
    }
}
