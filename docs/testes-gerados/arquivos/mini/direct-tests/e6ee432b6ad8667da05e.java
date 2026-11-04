/*
 * @test
 * @summary Test java.util.zip.ZipUtils.fileTimeToUnixTime(FileTime) conversion correctness
 * @run main ZipUtilsWitupTest
 */

import java.util.zip.ZipUtils;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {
    public static void main(String[] args) {
        testFileTimeToUnixTimeZero();
        testFileTimeToUnixTimeEpoch();
        testFileTimeToUnixTimePositive();
        testFileTimeToUnixTimeNegative();
    }

    private static void testFileTimeToUnixTimeZero() {
        FileTime ft = FileTime.from(0L, TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != 0L) {
            throw new AssertionError("Expected 0 but got " + result);
        }
    }

    private static void testFileTimeToUnixTimeEpoch() {
        // Unix epoch start
        FileTime ft = FileTime.from(0L, TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != 0L) {
            throw new AssertionError("Expected Unix epoch 0 but got " + result);
        }
    }

    private static void testFileTimeToUnixTimePositive() {
        // 1 hour after epoch
        long oneHourSeconds = 3600L;
        FileTime ft = FileTime.from(oneHourSeconds, TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != oneHourSeconds) {
            throw new AssertionError("Expected " + oneHourSeconds + " but got " + result);
        }
    }

    private static void testFileTimeToUnixTimeNegative() {
        // Negative time (before epoch)
        long negativeSeconds = -1000L;
        FileTime ft = FileTime.from(negativeSeconds, TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != negativeSeconds) {
            throw new AssertionError("Expected " + negativeSeconds + " but got " + result);
        }
    }
}
