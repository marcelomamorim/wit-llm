/*
 * @test
 * @summary Test java.util.zip.ZipUtils.unixTimeToFileTime(long) conversion correctness
 * @run main ZipUtilsWitupTest
 */

import java.util.zip.ZipUtils;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {
    public static void main(String[] args) {
        testUnixTimeToFileTimeZero();
        testUnixTimeToFileTimePositive();
        testUnixTimeToFileTimeNegative();
        testUnixTimeToFileTimeLargeValue();
    }

    private static void testUnixTimeToFileTimeZero() {
        long input = 0L;
        FileTime ft = ZipUtils.unixTimeToFileTime(input);
        if (ft.to(TimeUnit.SECONDS) != 0L) {
            throw new AssertionError("Expected 0 seconds, got " + ft.to(TimeUnit.SECONDS));
        }
    }

    private static void testUnixTimeToFileTimePositive() {
        long input = 1_500_000_000L; // A known positive unix timestamp
        FileTime ft = ZipUtils.unixTimeToFileTime(input);
        if (ft.to(TimeUnit.SECONDS) != input) {
            throw new AssertionError("Expected " + input + " seconds, got " + ft.to(TimeUnit.SECONDS));
        }
    }

    private static void testUnixTimeToFileTimeNegative() {
        long input = -1_000_000L; // Negative unix timestamp (before epoch)
        FileTime ft = ZipUtils.unixTimeToFileTime(input);
        if (ft.to(TimeUnit.SECONDS) != input) {
            throw new AssertionError("Expected " + input + " seconds, got " + ft.to(TimeUnit.SECONDS));
        }
    }

    private static void testUnixTimeToFileTimeLargeValue() {
        long input = Integer.MAX_VALUE; // 0x7fffffff, upper 32-bit unix time bound
        FileTime ft = ZipUtils.unixTimeToFileTime(input);
        if (ft.to(TimeUnit.SECONDS) != input) {
            throw new AssertionError("Expected " + input + " seconds, got " + ft.to(TimeUnit.SECONDS));
        }
    }
}
