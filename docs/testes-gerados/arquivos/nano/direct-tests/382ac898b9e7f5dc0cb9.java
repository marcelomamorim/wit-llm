/*
 * @test
 * @summary Test PrintStream constructor with File argument
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PrintStreamWitupTest {
    public static void main(String[] args) {
        testValidFile();
        testNullFile();
    }

    static void testValidFile() {
        try {
            File tempFile = File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            PrintStream ps = new PrintStream(tempFile);
            ps.println("Hello World");
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testValidFile", e);
        }
    }

    static void testNullFile() {
        try {
            new PrintStream((File) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testNullFile", e);
        }
    }
}