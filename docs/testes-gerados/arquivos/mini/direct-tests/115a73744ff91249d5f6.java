/*
 * @test
 * @summary Basic tests for OpenFileInputStreamAction constructors and run method
 * @run main OpenFileInputStreamActionWitupTest
 */

import sun.security.action.OpenFileInputStreamAction;
import java.io.*;

public class OpenFileInputStreamActionWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithString();
        testConstructorWithFile();
        testRunWithExistingFile();
        testRunWithNonExistingFile();
    }

    private static void testConstructorWithString() {
        String filename = "testfile.txt";
        OpenFileInputStreamAction action = new OpenFileInputStreamAction(filename);
        if (action == null) {
            throw new AssertionError("Constructor with String returned null");
        }
    }

    private static void testConstructorWithFile() {
        File file = new File("testfile.txt");
        OpenFileInputStreamAction action = new OpenFileInputStreamAction(file);
        if (action == null) {
            throw new AssertionError("Constructor with File returned null");
        }
    }

    private static void testRunWithExistingFile() throws Exception {
        File tempFile = File.createTempFile("OpenFileInputStreamActionTest", ".tmp");
        tempFile.deleteOnExit();
        OpenFileInputStreamAction action = new OpenFileInputStreamAction(tempFile.getAbsolutePath());
        FileInputStream fis = action.run();
        if (fis == null) {
            throw new AssertionError("run() returned null FileInputStream");
        }
        fis.close();
    }

    private static void testRunWithNonExistingFile() {
        File nonExistent = new File("nonexistentfile_123456789.tmp");
        OpenFileInputStreamAction action = new OpenFileInputStreamAction(nonExistent.getAbsolutePath());
        try {
            action.run();
            throw new AssertionError("Expected FileNotFoundException not thrown");
        } catch (FileNotFoundException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}