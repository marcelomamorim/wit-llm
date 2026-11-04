/* @test    @summary Test for ZipUtils.fileTimeToUnixTime method    @run main ZipUtilsWitupTest */

import java.util.zip.ZipUtils;
import java.nio.file.attribute.FileTime;

public class ZipUtilsWitupTest {

    public static void main(String[] args) {
        testFileTimeToUnixTimeWithEpoch();
        testFileTimeToUnixTimeWithCurrentTime();
        testFileTimeToUnixTimeWithMinValue();
        testFileTimeToUnixTimeWithMaxValue();
    }

    static void testFileTimeToUnixTimeWithEpoch() {
        // Create a FileTime representing epoch (0 seconds)
        FileTime ft = FileTime.from(0, java.util.concurrent.TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != 0) {
            throw new AssertionError("Expected 0, but got " + result);
        }
    }

    static void testFileTimeToUnixTimeWithCurrentTime() {
        long currentSeconds = System.currentTimeMillis() / 1000;
        FileTime ft = FileTime.from(currentSeconds, java.util.concurrent.TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != currentSeconds) {
            throw new AssertionError("Expected " + currentSeconds + ", but got " + result);
        }
    }

    static void testFileTimeToUnixTimeWithMinValue() {
        // Long.MIN_VALUE as FileTime
        FileTime ft = FileTime.from(Long.MIN_VALUE, java.util.concurrent.TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != Long.MIN_VALUE) {
            throw new AssertionError("Expected " + Long.MIN_VALUE + ", but got " + result);
        }
    }

    static void testFileTimeToUnixTimeWithMaxValue() {
        // Long.MAX_VALUE as FileTime
        FileTime ft = FileTime.from(Long.MAX_VALUE, java.util.concurrent.TimeUnit.SECONDS);
        long result = ZipUtils.fileTimeToUnixTime(ft);
        if (result != Long.MAX_VALUE) {
            throw new AssertionError("Expected " + Long.MAX_VALUE + ", but got " + result);
        }
    }
}
