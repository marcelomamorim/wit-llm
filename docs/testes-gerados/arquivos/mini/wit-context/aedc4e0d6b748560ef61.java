/*
 * @test
 * @summary Test FileOutputStream(String) constructor for null and valid inputs
 * @run main FileOutputStreamWitupTest
 */

import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testValidNameCreatesStream();
    }

    private static void testNullNameThrowsNPE() {
        try {
            new FileOutputStream((String) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException("Unexpected FileNotFoundException instead of NullPointerException", fnfe);
        }
    }

    private static void testValidNameCreatesStream() throws Exception {
        // Use a valid temporary file name
        String tmpFileName = System.getProperty("java.io.tmpdir") + "/FileOutputStreamWitupTest.tmp";
        java.io.File tmpFile = new java.io.File(tmpFileName);
        if (tmpFile.exists()) {
            if (!tmpFile.delete()) {
                throw new RuntimeException("Failed to delete existing temp file");
            }
        }

        // Creating FileOutputStream should create the file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tmpFileName);
            if (!tmpFile.exists()) {
                throw new RuntimeException("File was not created by FileOutputStream constructor");
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }
}
