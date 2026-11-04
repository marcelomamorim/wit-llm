/*
 * @test
 * @summary Test FileInputStream(String) constructor for exception and normal cases
 * @run main FileInputStreamWitupTest
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;

public class FileInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testNonExistentFileThrowsFileNotFoundException();
        testValidFileSucceeds();
    }

    private static void testNullNameThrowsNPE() {
        try {
            new FileInputStream((String) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        } catch (FileNotFoundException e) {
            // Not expected here, fail
            throw new RuntimeException("Unexpected FileNotFoundException instead of NullPointerException", e);
        }
    }

    private static void testNonExistentFileThrowsFileNotFoundException() {
        String nonExistentPath = "this_file_should_not_exist_1234567890.tmp";
        // Ensure file does not exist
        File f = new File(nonExistentPath);
        if (f.exists()) {
            if (!f.delete()) {
                throw new RuntimeException("Could not delete existing test file");
            }
        }
        try {
            new FileInputStream(nonExistentPath);
            throw new RuntimeException("Expected FileNotFoundException not thrown");
        } catch (FileNotFoundException e) {
            // Expected
        } catch (NullPointerException e) {
            // Not expected here, fail
            throw new RuntimeException("Unexpected NullPointerException instead of FileNotFoundException", e);
        }
    }

    private static void testValidFileSucceeds() throws Exception {
        // Create a temporary file
        File tempFile = File.createTempFile("FileInputStreamWitupTest", ".tmp");
        tempFile.deleteOnExit();

        // Open FileInputStream on the valid file path
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(tempFile.getAbsolutePath());
            if (fis == null) {
                throw new RuntimeException("FileInputStream constructor returned null");
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
