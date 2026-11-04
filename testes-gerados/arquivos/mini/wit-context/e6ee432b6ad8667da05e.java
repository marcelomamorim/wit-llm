/*
 * @test
 * @summary Test java.util.zip.ZipUtils.fileTimeToUnixTime(FileTime) method
 * @run main ZipUtilsWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipUtils;

public class ZipUtilsWitupTest {
    public static void main(String[] args) {
        testFileTimeToUnixTime();
    }

    private static void testFileTimeToUnixTime() {
        // Test with a valid FileTime representing epoch second 0
        FileTime epoch = FileTime.from(0L, TimeUnit.SECONDS);
        long unixTime = ZipUtils.fileTimeToUnixTime(epoch);
        if (unixTime != 0L) {
            throw new AssertionError("Expected 0 but got " + unixTime);
        }

        // Test with a FileTime representing a positive unix time
        FileTime ft = FileTime.from(123456789L, TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != 123456789L) {
            throw new AssertionError("Expected 123456789 but got " + result);
        }

        // Test with a FileTime representing a negative unix time (before epoch)
        FileTime negativeFt = FileTime.from(-1000L, TimeUnit.SECONDS);
        long negResult = ZipUtils.fileTimeToUnixTime(negativeFt);
        if (negResult != -1000L) {
            throw new AssertionError("Expected -1000 but got " + negResult);
        }

        // Test null input - according to current code, this will throw NullPointerException
        try {
            ZipUtils.fileTimeToUnixTime(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
