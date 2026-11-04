/*
 * @test
 * @summary Test java.util.zip.ZipUtils.unixTimeToFileTime(long) method
 * @run main ZipUtilsWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipUtils;

public class ZipUtilsWitupTest {
    public static void main(String[] args) {
        testUnixTimeToFileTime();
    }

    private static void testUnixTimeToFileTime() {
        long testTime = 0L; // epoch start
        FileTime ft = ZipUtils.unixTimeToFileTime(testTime);
        if (ft == null) {
            throw new AssertionError("FileTime should not be null");
        }
        long seconds = ft.to(TimeUnit.SECONDS);
        if (seconds != testTime) {
            throw new AssertionError("Converted FileTime seconds " + seconds + " != input " + testTime);
        }

        // Test with a positive unix time
        testTime = 1234567890L;
        ft = ZipUtils.unixTimeToFileTime(testTime);
        if (ft == null) {
            throw new AssertionError("FileTime should not be null for positive unix time");
        }
        seconds = ft.to(TimeUnit.SECONDS);
        if (seconds != testTime) {
            throw new AssertionError("Converted FileTime seconds " + seconds + " != input " + testTime);
        }

        // Test with a negative unix time (before epoch)
        testTime = -1234567890L;
        ft = ZipUtils.unixTimeToFileTime(testTime);
        if (ft == null) {
            throw new AssertionError("FileTime should not be null for negative unix time");
        }
        seconds = ft.to(TimeUnit.SECONDS);
        if (seconds != testTime) {
            throw new AssertionError("Converted FileTime seconds " + seconds + " != input " + testTime);
        }
    }
}
