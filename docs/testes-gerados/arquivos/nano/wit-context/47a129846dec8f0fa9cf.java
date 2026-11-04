/*
 * @test
 * @summary Test for ZipUtils.fileTimeToWinTime method, covering normal and null input scenarios.
 * @run main ZipUtilsWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipUtils;

public class ZipUtilsWitupTest {

    public static void main(String[] args) {
        testFileTimeToWinTimeWithValidFileTime();
        testFileTimeToWinTimeWithNullFileTime();
    }

    private static void testFileTimeToWinTimeWithValidFileTime() {
        try {
            long currentTimeMicros = System.currentTimeMillis() * 1000;
            FileTime ft = FileTime.from(currentTimeMicros, TimeUnit.MICROSECONDS);
            long result = ZipUtils.fileTimeToWinTime(ft);
            long expected = (currentTimeMicros - -11644473600000000L) * 10;
            if (result != expected) {
                throw new AssertionError("Expected: " + expected + ", but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid FileTime test", e);
        }
    }

    private static void testFileTimeToWinTimeWithNullFileTime() {
        try {
            ZipUtils.fileTimeToWinTime(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
