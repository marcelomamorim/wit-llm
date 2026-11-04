/*
 * @test
 * @summary Test FileOutputStream(File) constructor behavior
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFile();
        testNonExistentFile();
        testNullFile();
    }

    private static void testValidFile() throws Exception {
        File tempFile = File.createTempFile("foswitup", ".tmp");
        tempFile.deleteOnExit();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempFile);
            if (fos == null) {
                throw new RuntimeException("FileOutputStream constructor returned null");
            }
        } finally {
            if (fos != null) fos.close();
        }
    }

    private static void testNonExistentFile() throws Exception {
        File nonExistent = new File("nonexistent_dir/nonexistent_file.tmp");
        try {
            new FileOutputStream(nonExistent);
            throw new RuntimeException("Expected FileNotFoundException for non-existent file");
        } catch (FileNotFoundException expected) {
            // Expected exception
        }
    }

    private static void testNullFile() throws Exception {
        try {
            new FileOutputStream((File) null);
            throw new RuntimeException("Expected NullPointerException for null file");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
