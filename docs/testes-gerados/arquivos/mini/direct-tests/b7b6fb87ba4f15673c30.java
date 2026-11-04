/*
 * @test
 * @summary Test java.time.Duration.get(TemporalUnit) method behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DurationWitupTest {
    public static void main(String[] args) {
        testGetSeconds();
        testGetNanos();
        testGetUnsupportedUnit();
    }

    private static void testGetSeconds() {
        Duration d = Duration.ofSeconds(123, 456);
        long result = d.get(ChronoUnit.SECONDS);
        if (result != 123) {
            throw new AssertionError("Expected seconds 123 but got " + result);
        }
    }

    private static void testGetNanos() {
        Duration d = Duration.ofSeconds(123, 456789);
        long result = d.get(ChronoUnit.NANOS);
        if (result != 456789) {
            throw new AssertionError("Expected nanos 456789 but got " + result);
        }
    }

    private static void testGetUnsupportedUnit() {
        Duration d = Duration.ofSeconds(1);
        try {
            d.get(ChronoUnit.MINUTES);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for MINUTES unit");
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
        }
        try {
            d.get(null);
            throw new AssertionError("Expected NullPointerException for null unit");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
