/* @test    @summary Test FileInputStream constructor with valid and null filename    @run main FileInputStreamWitupTest */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

public class FileInputStreamWitupTest {

    public static void main(String[] args) {
        testValidFile();
        testNullFile();
    }

    private static void testValidFile() {
        try {
            File tempFile = File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            try (FileInputStream fis = new FileInputStream(tempFile.getAbsolutePath())) {
                if (fis == null) {
                    throw new AssertionError("FileInputStream should not be null for valid file");
                }
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidFile: " + e);
        }
    }

    private static void testNullFile() {
        try {
            new FileInputStream((String) null);
            throw new AssertionError("Expected NullPointerException for null filename");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (FileNotFoundException e) {
            // This can occur if null is interpreted as a filename, but in Java, passing null should throw NullPointerException
            throw new AssertionError("Unexpected FileNotFoundException for null filename");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNullFile: " + e);
        }
    }
}
