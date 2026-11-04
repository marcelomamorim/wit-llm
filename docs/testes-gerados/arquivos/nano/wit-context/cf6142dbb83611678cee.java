/*
 * @test
 * @summary Test for ZipUtils.unixTimeToFileTime(long)
 * @run main ZipUtilsWitupTest
 */

import java.util.zip.ZipUtils;
import java.nio.file.attribute.FileTime;

public class ZipUtilsWitupTest {

    public static void main(String[] args) {
        testUnixTimeToFileTimeNormal();
        testUnixTimeToFileTimeZero();
        testUnixTimeToFileTimeNegative();
    }

    private static void testUnixTimeToFileTimeNormal() {
        long utime = 1609459200L; // 2021-01-01T00:00:00Z
        FileTime ft = ZipUtils.unixTimeToFileTime(utime);
        long expected = utime;
        long actual = ft.to(java.util.concurrent.TimeUnit.SECONDS);
        if (expected != actual) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }
    }

    private static void testUnixTimeToFileTimeZero() {
        long utime = 0L; // epoch
        FileTime ft = ZipUtils.unixTimeToFileTime(utime);
        long expected = utime;
        long actual = ft.to(java.util.concurrent.TimeUnit.SECONDS);
        if (expected != actual) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }
    }

    private static void testUnixTimeToFileTimeNegative() {
        long utime = -1L; // before epoch
        FileTime ft = ZipUtils.unixTimeToFileTime(utime);
        long expected = utime;
        long actual = ft.to(java.util.concurrent.TimeUnit.SECONDS);
        if (expected != actual) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }
    }
}
