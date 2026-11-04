/*
 * @test
 * @summary Test FileTime.to(TimeUnit) method behavior
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import java.time.Instant;

public class FileTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testConvertWithUnitField();
        testConvertWithInstantNormal();
        testConvertWithInstantOverflowMin();
        testConvertWithInstantOverflowMax();
    }

    private static void testConvertWithUnitField() {
        // Create FileTime from value and unit (unit != null)
        FileTime ft = FileTime.from(2, TimeUnit.SECONDS);
        // Convert to MILLISECONDS
        long millis = ft.to(TimeUnit.MILLISECONDS);
        // 2 seconds = 2000 milliseconds
        if (millis != 2000L) {
            throw new AssertionError("Expected 2000 ms but got " + millis);
        }
        // Convert to SECONDS (same unit)
        long secs = ft.to(TimeUnit.SECONDS);
        if (secs != 2L) {
            throw new AssertionError("Expected 2 seconds but got " + secs);
        }
    }

    private static void testConvertWithInstantNormal() {
        // Create FileTime from Instant with known epoch second and nanos
        Instant instant = Instant.ofEpochSecond(1, 500_000_000); // 1.5 seconds
        FileTime ft = FileTime.from(instant);
        // Convert to MILLISECONDS
        long millis = ft.to(TimeUnit.MILLISECONDS);
        // 1.5 seconds = 1500 milliseconds
        if (millis != 1500L) {
            throw new AssertionError("Expected 1500 ms but got " + millis);
        }
        // Convert to SECONDS
        long secs = ft.to(TimeUnit.SECONDS);
        // nanos part truncated to 0 seconds
        if (secs != 1L) {
            throw new AssertionError("Expected 1 second but got " + secs);
        }
    }

    private static void testConvertWithInstantOverflowMin() {
        // Create Instant with epoch second Long.MIN_VALUE
        Instant instant = Instant.ofEpochSecond(Long.MIN_VALUE, 0);
        FileTime ft = FileTime.from(instant);
        long result = ft.to(TimeUnit.SECONDS);
        if (result != Long.MIN_VALUE) {
            throw new AssertionError("Expected Long.MIN_VALUE but got " + result);
        }
    }

    private static void testConvertWithInstantOverflowMax() {
        // Create Instant with epoch second Long.MAX_VALUE
        Instant instant = Instant.ofEpochSecond(Long.MAX_VALUE, 0);
        FileTime ft = FileTime.from(instant);
        long result = ft.to(TimeUnit.SECONDS);
        if (result != Long.MAX_VALUE) {
            throw new AssertionError("Expected Long.MAX_VALUE but got " + result);
        }
    }
}
