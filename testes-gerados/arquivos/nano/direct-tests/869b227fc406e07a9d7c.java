/* @test    @summary Test FileOutputStream constructor with existing File object    @run main FileOutputStreamWitupTest */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithExistingFile();
    }

    public static void testConstructorWithExistingFile() throws Exception {
        File tempFile = File.createTempFile("testFile", ".txt");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            if (fos == null) {
                throw new AssertionError("FileOutputStream instance is null");
            }
        }
        if (!tempFile.exists()) {
            throw new AssertionError("Temporary file was not created");
        }
        // Clean up
        if (!tempFile.delete()) {
            throw new AssertionError("Failed to delete temporary file");
        }
    }
}
