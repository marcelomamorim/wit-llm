/*
 * @test
 * @summary Test setLastModifiedTime method of ZipEntry for various FileTime inputs
 * @run main ZipEntryWitupTest
 */

import java.util.zip.ZipEntry;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class ZipEntryWitupTest {

    public static void main(String[] args) {
        testSetLastModifiedTimeWithCurrentTime();
        testSetLastModifiedTimeWithEpoch();
        testSetLastModifiedTimeWithNull();
    }

    static void testSetLastModifiedTimeWithCurrentTime() {
        ZipEntry entry = new ZipEntry("test.txt");
        FileTime currentTime = FileTime.from(Instant.now());
        ZipEntry result = entry.setLastModifiedTime(currentTime);
        assert result == entry : "setLastModifiedTime should return the same instance";
        assert entry.mtime.equals(currentTime) : "mtime should be set to currentTime";
        long expectedDosTime = javaToExtendedDosTime(currentTime.to(TimeUnit.MILLISECONDS));
        assert entry.xdostime == expectedDosTime : "xdostime should match converted currentTime";
    }

    static void testSetLastModifiedTimeWithEpoch() {
        ZipEntry entry = new ZipEntry("epoch.txt");
        FileTime epochTime = FileTime.from(Instant.ofEpochMilli(0));
        ZipEntry result = entry.setLastModifiedTime(epochTime);
        assert result == entry : "setLastModifiedTime should return the same instance";
        assert entry.mtime.equals(epochTime) : "mtime should be set to epochTime";
        long expectedDosTime = javaToExtendedDosTime(epochTime.to(TimeUnit.MILLISECONDS));
        assert entry.xdostime == expectedDosTime : "xdostime should match converted epochTime";
    }

    static void testSetLastModifiedTimeWithNull() {
        try {
            ZipEntry entry = new ZipEntry("null.txt");
            entry.setLastModifiedTime(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static long javaToExtendedDosTime(long millis) {
        // Simplified conversion for testing purposes
        // In real implementation, this would convert milliseconds to DOS time format
        // Here, just return the millis for comparison
        return millis;
    }
}
