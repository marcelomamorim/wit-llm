/*
 * @test
 * @summary Test OpenFileInputStreamAction constructors and run method for expected behavior and exceptions
 * @run main OpenFileInputStreamActionWitupTest
 */

import java.io.*;
import sun.security.action.OpenFileInputStreamAction;

public class OpenFileInputStreamActionWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidString();
        testConstructorWithNullString();
        testConstructorWithValidFile();
        testRunWithExistingFile();
        testRunWithNonExistingFile();
    }

    private static void testConstructorWithValidString() {
        String validPath = System.getProperty("java.io.tmpdir");
        OpenFileInputStreamAction action = new OpenFileInputStreamAction(validPath);
        if (action == null) {
            throw new AssertionError("Constructor with valid string returned null");
        }
    }

    private static void testConstructorWithNullString() {
        try {
            new OpenFileInputStreamAction((String) null);
            throw new AssertionError("Expected NullPointerException for null filename");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidFile() {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        OpenFileInputStreamAction action = new OpenFileInputStreamAction(tmpDir);
        if (action == null) {
            throw new AssertionError("Constructor with valid File returned null");
        }
    }

    private static void testRunWithExistingFile() throws Exception {
        File tmpFile = File.createTempFile("OpenFileInputStreamActionTest", ".tmp");
        tmpFile.deleteOnExit();
        OpenFileInputStreamAction action = new OpenFileInputStreamAction(tmpFile);
        FileInputStream fis = null;
        try {
            fis = action.run();
            if (fis == null) {
                throw new AssertionError("run() returned null FileInputStream");
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            tmpFile.delete();
        }
    }

    private static void testRunWithNonExistingFile() throws Exception {
        File nonExisting = new File(System.getProperty("java.io.tmpdir"), "nonexistentfile123456789.tmp");
        OpenFileInputStreamAction action = new OpenFileInputStreamAction(nonExisting);
        try {
            action.run();
            throw new AssertionError("Expected FileNotFoundException for non-existing file");
        } catch (FileNotFoundException expected) {
            // Expected exception
        }
    }
}
