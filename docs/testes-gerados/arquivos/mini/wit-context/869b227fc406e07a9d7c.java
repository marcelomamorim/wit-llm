/*
 * @test
 * @summary Test FileOutputStream(File) constructor for basic behavior and exception safety
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFile();
        testNullFile();
    }

    private static void testValidFile() throws Exception {
        // Create a temporary file that will be deleted on exit
        File tempFile = File.createTempFile("foswitup", ".tmp");
        tempFile.deleteOnExit();

        // Construct FileOutputStream with the file, should not throw
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempFile);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    private static void testNullFile() {
        // According to source and WIT, passing null should throw NullPointerException
        try {
            new FileOutputStream((File) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (FileNotFoundException fnfe) {
            // FileNotFoundException is not expected for null input, fail
            throw new RuntimeException("Unexpected FileNotFoundException for null input", fnfe);
        }
    }
}
