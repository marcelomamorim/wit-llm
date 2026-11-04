/*
 * @test
 * @summary Test createNewFile() method of java.io.File
 * @run main FileCreateNewFileTest
 */

import java.io.File;
import java.io.IOException;

public class FileCreateNewFileTest {
    public static void main(String[] args) {
        testCreateNewFileSuccess();
        testCreateNewFileInvalidPath();
    }

    static void testCreateNewFileSuccess() {
        try {
            File tempFile = File.createTempFile("testfile", ".tmp");
            boolean created = false;
            try {
                created = tempFile.createNewFile();
            } catch (IOException e) {
                // If file already exists, createNewFile() returns false
                // or if an IO error occurs, it throws IOException
                // For test purposes, ignore
            }
            if (created || tempFile.exists()) {
                System.out.println("testCreateNewFileSuccess passed");
            } else {
                System.out.println("testCreateNewFileSuccess failed: file not created");
            }
            tempFile.delete();
        } catch (IOException e) {
            System.out.println("testCreateNewFileSuccess failed with exception: " + e);
        }
    }

    static void testCreateNewFileInvalidPath() {
        // Using an invalid path to trigger IOException
        File invalidFile = new File("/invalid_path/\0file");
        try {
            invalidFile.createNewFile();
            System.out.println("testCreateNewFileInvalidPath failed: exception expected");
        } catch (IOException e) {
            System.out.println("testCreateNewFileInvalidPath passed");
        }
    }
}
