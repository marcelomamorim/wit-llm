/*
 * @test
 * @summary Test java.util.zip.ZipUtils.fileTimeToWinTime conversion correctness
 * @run main ZipUtilsWitupTest
 */

import java.util.zip.ZipUtils;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {
    public static void main(String[] args) {
        testFileTimeToWinTimeZero();
        testFileTimeToWinTimePositive();
        testFileTimeToWinTimeNegative();
    }

    private static void testFileTimeToWinTimeZero() {
        FileTime ft = FileTime.from(ZipUtils.WINDOWS_EPOCH_IN_MICROSECONDS, TimeUnit.MICROSECONDS);
        long winTime = ZipUtils.fileTimeToWinTime(ft);
        if (winTime != 0L) {
            throw new AssertionError("Expected Windows time 0 for Windows epoch FileTime, got: " + winTime);
        }
    }

    private static void testFileTimeToWinTimePositive() {
        // FileTime 1,000,000 microseconds after Windows epoch
        long microsAfterEpoch = ZipUtils.WINDOWS_EPOCH_IN_MICROSECONDS + 1_000_000L;
        FileTime ft = FileTime.from(microsAfterEpoch, TimeUnit.MICROSECONDS);
        long winTime = ZipUtils.fileTimeToWinTime(ft);
        long expected = (microsAfterEpoch - ZipUtils.WINDOWS_EPOCH_IN_MICROSECONDS) * 10;
        if (winTime != expected) {
            throw new AssertionError("Expected Windows time " + expected + ", got: " + winTime);
        }
    }

    private static void testFileTimeToWinTimeNegative() {
        // FileTime before Windows epoch
        long microsBeforeEpoch = ZipUtils.WINDOWS_EPOCH_IN_MICROSECONDS - 500_000L;
        FileTime ft = FileTime.from(microsBeforeEpoch, TimeUnit.MICROSECONDS);
        long winTime = ZipUtils.fileTimeToWinTime(ft);
        long expected = (microsBeforeEpoch - ZipUtils.WINDOWS_EPOCH_IN_MICROSECONDS) * 10;
        if (winTime != expected) {
            throw new AssertionError("Expected Windows time " + expected + ", got: " + winTime);
        }
    }
}
