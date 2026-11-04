/*
 * @test
 * @summary Test java.io.File.createNewFile() for expected behavior and exception
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest {
    public static void main(String[] args) throws Exception {
        testCreateNewFileValid();
        testCreateNewFileInvalidPath();
        testCreateNewFileSecurityException();
    }

    private static void testCreateNewFileValid() throws IOException {
        // Create a temporary file path that should be valid
        File tempFile = new File(System.getProperty("java.io.tmpdir"), "FileWitupTest_tempfile.tmp");
        // Delete if exists to ensure createNewFile can create it
        if (tempFile.exists()) {
            tempFile.delete();
        }
        boolean created = tempFile.createNewFile();
        if (!created && !tempFile.exists()) {
            throw new RuntimeException("createNewFile() returned false and file does not exist");
        }
        // Clean up
        tempFile.delete();
    }

    private static void testCreateNewFileInvalidPath() {
        // Use a path that is invalid (e.g., empty string or illegal characters)
        // Since File constructor allows any string, we try empty string which is invalid
        File invalidFile = new File("");
        try {
            invalidFile.createNewFile();
            throw new RuntimeException("Expected IOException for invalid path not thrown");
        } catch (IOException e) {
            // Expected IOException for invalid path
        }
    }

    private static void testCreateNewFileSecurityException() {
        // Install a SecurityManager that denies write access to the test file path
        SecurityManager original = System.getSecurityManager();
        try {
            System.setSecurityManager(new SecurityManager() {
                @Override
                public void checkWrite(String file) {
                    if (file != null && file.contains("FileWitupTest_denied.tmp")) {
                        throw new SecurityException("Write access denied by test SecurityManager");
                    }
                }

                @Override
                public void checkPermission(java.security.Permission perm) {
                    // Allow other permissions
                }
            });

            File deniedFile = new File(System.getProperty("java.io.tmpdir"), "FileWitupTest_denied.tmp");
            try {
                deniedFile.createNewFile();
                throw new RuntimeException("Expected SecurityException not thrown");
            } catch (SecurityException se) {
                // Expected SecurityException
            } catch (IOException ioe) {
                // IOException may be thrown if file system denies creation, accept it
            }

        } finally {
            System.setSecurityManager(original);
        }
    }
}
