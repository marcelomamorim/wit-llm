/*
 * @test
 * @summary Test java.io.File.setLastModified(long) method behavior
 * @run main FileWitupTest_P23d68a09251c
 */

import java.io.File;

public class FileWitupTest_P23d68a09251c {
    public static void main(String[] args) throws Exception {
        testNegativeTimeThrows();
        testSetLastModifiedOnInvalidFileReturnsFalse();
        testSetLastModifiedOnValidFile();
    }

    private static void testNegativeTimeThrows() {
        File f = new File("testfile.tmp");
        try {
            f.setLastModified(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative time");
        } catch (IllegalArgumentException e) {
            if (!"Negative time".equals(e.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch", e);
            }
        }
    }

    private static void testSetLastModifiedOnInvalidFileReturnsFalse() {
        // Create a File with an invalid path (empty string is invalid on some platforms)
        File invalidFile = new File("");
        // The method should return false for invalid file
        boolean result = invalidFile.setLastModified(System.currentTimeMillis());
        if (result) {
            throw new RuntimeException("Expected setLastModified to return false for invalid file");
        }
    }

    private static void testSetLastModifiedOnValidFile() throws Exception {
        // Create a temporary file
        File tempFile = File.createTempFile("FileWitupTest_P23d68a09251c", ".tmp");
        tempFile.deleteOnExit();

        long newTime = System.currentTimeMillis() - 10000L; // 10 seconds ago
        boolean result = tempFile.setLastModified(newTime);

        if (!result) {
            throw new RuntimeException("setLastModified returned false on valid file");
        }

        long lastModified = tempFile.lastModified();
        if (lastModified != newTime) {
            throw new RuntimeException("lastModified time was not updated correctly");
        }
    }
}
