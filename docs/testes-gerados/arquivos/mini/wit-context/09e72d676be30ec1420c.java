/*
 * @test
 * @summary Test FileTime.to(TimeUnit) method behavior including null argument and normal conversions
 * @run main FileTimeWitupTest
 */

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import java.time.Instant;

public class FileTimeWitupTest {
    public static void main(String[] args) {
        testNullUnitThrowsNPE();
        testConversionFromValueUnit();
        testConversionFromInstant();
        testConversionOverflowHandling();
        System.out.println("PASS");
    }

    private static void testNullUnitThrowsNPE() {
        FileTime ft = FileTime.fromMillis(1000L);
        try {
            ft.to(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testConversionFromValueUnit() {
        // Create FileTime from 2 seconds (2000 milliseconds)
        FileTime ft = FileTime.from(2L, TimeUnit.SECONDS);
        // Convert to milliseconds
        long ms = ft.to(TimeUnit.MILLISECONDS);
        if (ms != 2000L) {
            throw new RuntimeException("Conversion from seconds to milliseconds failed: " + ms);
        }
        // Convert to seconds (should be 2)
        long s = ft.to(TimeUnit.SECONDS);
        if (s != 2L) {
            throw new RuntimeException("Conversion to same unit failed: " + s);
        }
        // Convert to microseconds (should be 2_000_000)
        long us = ft.to(TimeUnit.MICROSECONDS);
        if (us != 2_000_000L) {
            throw new RuntimeException("Conversion to microseconds failed: " + us);
        }
    }

    private static void testConversionFromInstant() {
        Instant instant = Instant.ofEpochSecond(1, 500_000_000); // 1.5 seconds
        FileTime ft = FileTime.from(instant);
        // Convert to milliseconds (should be 1500)
        long ms = ft.to(TimeUnit.MILLISECONDS);
        if (ms != 1500L) {
            throw new RuntimeException("Conversion from Instant to milliseconds failed: " + ms);
        }
        // Convert to seconds (should be 1)
        long s = ft.to(TimeUnit.SECONDS);
        if (s != 1L) {
            throw new RuntimeException("Conversion from Instant to seconds failed: " + s);
        }
    }

    private static void testConversionOverflowHandling() {
        // Instant with very large epoch seconds to trigger overflow
        Instant instantMax = Instant.ofEpochSecond(Long.MAX_VALUE, 0);
        FileTime ftMax = FileTime.from(instantMax);
        long valMax = ftMax.to(TimeUnit.NANOSECONDS);
        if (valMax != Long.MAX_VALUE) {
            throw new RuntimeException("Expected Long.MAX_VALUE on overflow, got: " + valMax);
        }

        Instant instantMin = Instant.ofEpochSecond(Long.MIN_VALUE, 0);
        FileTime ftMin = FileTime.from(instantMin);
        long valMin = ftMin.to(TimeUnit.NANOSECONDS);
        if (valMin != Long.MIN_VALUE) {
            throw new RuntimeException("Expected Long.MIN_VALUE on underflow, got: " + valMin);
        }
    }
}
