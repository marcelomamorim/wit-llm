/*
 * @test
 * @summary Test java.util.zip.ZipUtils.fileTimeToWinTime(FileTime) method
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
        // Create a FileTime representing 0 microseconds since epoch
        FileTime ft = FileTime.from(0, TimeUnit.MICROSECONDS);
        long winTime = ZipUtils.fileTimeToWinTime(ft);
        // The result should be (0 - WINDOWS_EPOCH_IN_MICROSECONDS) * 10
        // WINDOWS_EPOCH_IN_MICROSECONDS = -11644473600000000L
        long expected = (0 - (-11644473600000000L)) * 10;
        if (winTime != expected) {
            throw new AssertionError("fileTimeToWinTime returned " + winTime + ", expected " + expected);
        }
    }

    private static void testFileTimeToWinTimeWithNullFileTime() {
        // According to WIT expaths, passing null should throw NullPointerException
        try {
            ZipUtils.fileTimeToWinTime(null);
            throw new AssertionError("Expected NullPointerException when passing null FileTime");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
