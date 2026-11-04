/* @test    @summary Test for ZipUtils.unixTimeToFileTime method    @run main ZipUtilsWitupTest */

import java.util.zip.ZipUtils;
import java.nio.file.attribute.FileTime;

public class ZipUtilsWitupTest {

    public static void main(String[] args) {
        testUnixTimeToFileTimeZero();
        testUnixTimeToFileTimePositive();
        testUnixTimeToFileTimeNegative();
    }

    static void testUnixTimeToFileTimeZero() {
        long utime = 0L;
        FileTime ft = ZipUtils.unixTimeToFileTime(utime);
        long expected = utime;
        long actual = ft.to(TimeUnit.SECONDS);
        if (actual != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }
    }

    static void testUnixTimeToFileTimePositive() {
        long utime = 1609459200L; // 2021-01-01 00:00:00 UTC
        FileTime ft = ZipUtils.unixTimeToFileTime(utime);
        long actual = ft.to(TimeUnit.SECONDS);
        if (actual != utime) {
            throw new AssertionError("Expected: " + utime + ", but got: " + actual);
        }
    }

    static void testUnixTimeToFileTimeNegative() {
        long utime = -1L; // before epoch
        FileTime ft = ZipUtils.unixTimeToFileTime(utime);
        long actual = ft.to(TimeUnit.SECONDS);
        if (actual != utime) {
            throw new AssertionError("Expected: " + utime + ", but got: " + actual);
        }
    }
}
