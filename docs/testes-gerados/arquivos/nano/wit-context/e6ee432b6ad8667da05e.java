/*
 * @test
 * @summary Test for java.util.zip.ZipUtils.fileTimeToUnixTime method.
 * @run main ZipUtilsWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class ZipUtilsWitupTest {

    public static void main(String[] args) {
        testFileTimeToUnixTimeWithValidFileTime();
        testFileTimeToUnixTimeWithNullFileTime();
    }

    private static void testFileTimeToUnixTimeWithValidFileTime() {
        try {
            long currentTime = System.currentTimeMillis();
            FileTime ft = FileTime.from(currentTime, TimeUnit.MILLISECONDS);
            long result = java.util.zip.ZipUtils.fileTimeToUnixTime(ft);
            long expected = TimeUnit.SECONDS.convert(currentTime, TimeUnit.MILLISECONDS);
            if (result != expected) {
                throw new AssertionError("Expected: " + expected + ", but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testFileTimeToUnixTimeWithValidFileTime", e);
        }
    }

    private static void testFileTimeToUnixTimeWithNullFileTime() {
        try {
            java.util.zip.ZipUtils.fileTimeToUnixTime(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
