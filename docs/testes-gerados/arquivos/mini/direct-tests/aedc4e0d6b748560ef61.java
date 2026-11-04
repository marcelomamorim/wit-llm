/*
 * @test
 * @summary Test FileOutputStream(String name) constructor behavior
 * @run main FileOutputStreamWitupTest
 */

import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileName();
        testNullFileName();
        testNonExistentDirectory();
    }

    private static void testValidFileName() throws Exception {
        File tempFile = File.createTempFile("foswitup", ".tmp");
        tempFile.deleteOnExit();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempFile.getAbsolutePath());
            if (fos == null) {
                throw new RuntimeException("FileOutputStream is null for valid file name");
            }
        } finally {
            if (fos != null) fos.close();
        }
    }

    private static void testNullFileName() {
        try {
            new FileOutputStream((String) null);
            throw new RuntimeException("Expected NullPointerException for null file name");
        } catch (NullPointerException e) {
            // Expected
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Expected NullPointerException, got FileNotFoundException", e);
        }
    }

    private static void testNonExistentDirectory() {
        String path = "nonexistent_dir/subdir/file.tmp";
        try {
            new FileOutputStream(path);
            throw new RuntimeException("Expected FileNotFoundException for non-existent directory");
        } catch (FileNotFoundException e) {
            // Expected
        }
    }
}
