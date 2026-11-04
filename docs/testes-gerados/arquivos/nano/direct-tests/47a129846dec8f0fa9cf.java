/* @test    @summary Test for ZipUtils.fileTimeToWinTime method    @run main ZipUtilsWitupTest */

import java.util.zip.ZipUtils;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import static java.util.concurrent.TimeUnit.*;

public class ZipUtilsWitupTest {

    public static void main(String[] args) {
        testFileTimeToWinTimeWithZero();
        testFileTimeToWinTimeWithPositive();
        testFileTimeToWinTimeWithNegative();
    }

    static void testFileTimeToWinTimeWithZero() {
        FileTime ft = FileTime.from(0, MICROSECONDS);
        long result = ZipUtils.fileTimeToWinTime(ft);
        long expected = (-11644473600000000L) * 10;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testFileTimeToWinTimeWithPositive() {
        long microseconds = 123456789L;
        FileTime ft = FileTime.from(microseconds, MICROSECONDS);
        long result = ZipUtils.fileTimeToWinTime(ft);
        long expected = (microseconds - (-11644473600000000L)) * 10;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testFileTimeToWinTimeWithNegative() {
        long microseconds = -987654321L;
        FileTime ft = FileTime.from(microseconds, MICROSECONDS);
        long result = ZipUtils.fileTimeToWinTime(ft);
        long expected = (microseconds - (-11644473600000000L)) * 10;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
