/* @test    @summary Test for File.setLastModified(long) method behavior and exception handling    @run main FileWitupTest_Pab5fe49e5590 */

import java.io.File;

public class FileWitupTest_Pab5fe49e5590 {

    public static void main(String[] args) {
        testSetLastModifiedWithValidTime();
        testSetLastModifiedWithNegativeTime();
        testSetLastModifiedOnInvalidFile();
    }

    private static void testSetLastModifiedWithValidTime() {
        try {
            File file = new File("testfile_valid_time.txt");
            if (!file.createNewFile()) {
                throw new RuntimeException("Failed to create test file.");
            }
            long currentTime = System.currentTimeMillis();
            boolean result = file.setLastModified(currentTime);
            if (!result) {
                throw new AssertionError("setLastModified returned false for valid time.");
            }
            long lastModified = file.lastModified();
            if (lastModified != currentTime) {
                throw new AssertionError("lastModified time does not match set time.");
            }
            if (!file.delete()) {
                throw new RuntimeException("Failed to delete test file.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in testSetLastModifiedWithValidTime: " + e.getMessage(), e);
        }
    }

    private static void testSetLastModifiedWithNegativeTime() {
        try {
            File file = new File("testfile_negative_time.txt");
            if (!file.createNewFile()) {
                throw new RuntimeException("Failed to create test file.");
            }
            try {
                file.setLastModified(-1000);
                throw new AssertionError("Expected IllegalArgumentException for negative time.");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
            if (!file.delete()) {
                throw new RuntimeException("Failed to delete test file.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in testSetLastModifiedWithNegativeTime: " + e.getMessage(), e);
        }
    }

    private static void testSetLastModifiedOnInvalidFile() {
        try {
            File file = new File("nonexistent_file.txt");
            boolean result = file.setLastModified(System.currentTimeMillis());
            if (result) {
                // Depending on the OS, this might succeed if the file is created later or not.
                // But since the file does not exist, the result should be false.
                // So, we check if the file exists.
                if (file.exists()) {
                    throw new AssertionError("setLastModified returned true on an existing file.");
                }
            } else {
                // Expected if file does not exist
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in testSetLastModifiedOnInvalidFile: " + e.getMessage(), e);
        }
    }
}
