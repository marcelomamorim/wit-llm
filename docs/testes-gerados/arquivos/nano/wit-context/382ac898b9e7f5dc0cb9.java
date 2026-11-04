/* @test    @summary Test for PrintStream(File) constructor and exception path    @run main PrintStreamWitupTest */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testPrintStreamConstructorWithValidFile();
        testPrintStreamConstructorWithInvalidFile();
    }

    private static void testPrintStreamConstructorWithValidFile() {
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            tempFile.deleteOnExit();
            PrintStream ps = new PrintStream(tempFile);
            if (ps != null) {
                System.out.println("testPrintStreamConstructorWithValidFile: PASSED");
                ps.close();
            } else {
                System.err.println("testPrintStreamConstructorWithValidFile: FAILED - null PrintStream");
            }
        } catch (Exception e) {
            System.err.println("testPrintStreamConstructorWithValidFile: FAILED with exception: " + e);
        }
    }

    private static void testPrintStreamConstructorWithInvalidFile() {
        try {
            // Using a directory path instead of a file to trigger FileNotFoundException
            File dir = new File(System.getProperty("java.io.tmpdir"));
            PrintStream ps = new PrintStream(dir);
            ps.close();
            System.err.println("testPrintStreamConstructorWithInvalidFile: FAILED - no exception thrown");
        } catch (FileNotFoundException e) {
            System.out.println("testPrintStreamConstructorWithInvalidFile: PASSED");
        } catch (Exception e) {
            System.err.println("testPrintStreamConstructorWithInvalidFile: FAILED with unexpected exception: " + e);
        }
    }
}
