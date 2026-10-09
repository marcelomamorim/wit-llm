/*
 * @test
 * @summary Test java.io.File.setLastModified(long) for negative time and normal usage
 * @run main FileWitupTest_P23d68a09251c
 */

import java.io.File;
import java.io.IOException;

public class FileWitupTest_P23d68a09251c {
    public static void main(String[] args) throws IOException {
        testSetLastModifiedNegativeTime();
        testSetLastModifiedNormal();
    }

    private static void testSetLastModifiedNegativeTime() {
        File f = new File("testfile.tmp");
        // Create file to ensure it exists for setLastModified
        try {
            if (!f.exists()) {
                boolean created = f.createNewFile();
                if (!created) {
                    throw new RuntimeException("Failed to create test file");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during test setup", e);
        }

        boolean threwIAE = false;
        try {
            f.setLastModified(-1L);
        } catch (IllegalArgumentException e) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("Expected IllegalArgumentException for negative time");
        }

        // Clean up
        f.delete();
    }

    private static void testSetLastModifiedNormal() {
        File f = new File("testfile.tmp");
        try {
            if (!f.exists()) {
                boolean created = f.createNewFile();
                if (!created) {
                    throw new RuntimeException("Failed to create test file");
                }
            }

            long now = System.currentTimeMillis();
            boolean result = f.setLastModified(now);
            if (!result) {
                throw new RuntimeException("setLastModified returned false for valid time");
            }

            long lastMod = f.lastModified();
            if (lastMod != now) {
                // On some platforms, lastModified may not be set exactly,
                // so allow some tolerance (1000 ms)
                if (Math.abs(lastMod - now) > 1000) {
                    throw new RuntimeException("lastModified time not set correctly");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during test", e);
        } finally {
            f.delete();
        }
    }
}
