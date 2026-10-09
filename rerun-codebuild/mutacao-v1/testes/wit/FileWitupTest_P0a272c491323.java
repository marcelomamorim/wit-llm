/*
 * @test
 * @summary Test createNewFile() method for various scenarios.
 * @run main FileWitupTest_P0a272c491323
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest_P0a272c491323 {

    public static void main(String[] args) throws Exception {
        testCreateNewFileNormal();
        testCreateNewFileInvalidPath();
        testCreateNewFileWithSecurityManager();
    }

    static void testCreateNewFileNormal() throws IOException {
        File file = new File("temp_test_file.txt");
        if (file.exists()) {
            if (!file.delete()) {
                throw new RuntimeException("Failed to delete existing test file");
            }
        }
        boolean created = false;
        try {
            created = file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in normal case", e);
        }
        if (!created) {
            throw new AssertionError("Expected createNewFile() to return true for new file");
        }
        if (!file.exists()) {
            throw new AssertionError("File should exist after creation");
        }
        if (!file.delete()) {
            throw new RuntimeException("Failed to delete test file after creation");
        }
    }

    static void testCreateNewFileInvalidPath() {
        File invalidFile = new File("/invalid_path/testfile.txt");
        try {
            invalidFile.createNewFile();
            throw new AssertionError("Expected IOException for invalid path");
        } catch (IOException e) {
            // Expected exception
        }
    }

    static void testCreateNewFileWithSecurityManager() throws IOException {
        SecurityManager originalSecurity = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkWrite(String file) {
                throw new SecurityException("Write permission denied");
            }
        });
        File file = new File("temp_security_test.txt");
        try {
            try {
                file.createNewFile();
                throw new AssertionError("Expected SecurityException or IOException");
            } catch (SecurityException | IOException e) {
                // Expected exception
            }
        } finally {
            System.setSecurityManager(originalSecurity);
        }
    }
}
