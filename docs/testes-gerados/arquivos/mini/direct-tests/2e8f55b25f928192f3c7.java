/*
 * @test
 * @summary Test FileInputStream(String) constructor behavior
 * @run main FileInputStreamWitupTest
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileName();
        testNullFileName();
        testNonExistentFile();
    }

    private static void testValidFileName() throws Exception {
        // Create a temporary file to test
        java.io.File tempFile = java.io.File.createTempFile("FileInputStreamWitupTest", ".tmp");
        tempFile.deleteOnExit();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(tempFile.getPath());
            if (fis == null) {
                throw new RuntimeException("FileInputStream constructor returned null");
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    private static void testNullFileName() {
        try {
            new FileInputStream((String) null);
            throw new RuntimeException("Expected NullPointerException for null filename");
        } catch (NullPointerException e) {
            // Expected
        } catch (FileNotFoundException e) {
            // Not expected, fail
            throw new RuntimeException("Expected NullPointerException, got FileNotFoundException", e);
        }
    }

    private static void testNonExistentFile() {
        try {
            new FileInputStream("this_file_should_not_exist_1234567890.tmp");
            throw new RuntimeException("Expected FileNotFoundException for non-existent file");
        } catch (FileNotFoundException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Expected FileNotFoundException, got " + e.getClass().getName(), e);
        }
    }
}
