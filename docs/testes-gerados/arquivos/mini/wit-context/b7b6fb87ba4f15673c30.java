/*
 * @test
 * @summary Test java.time.Duration.get(TemporalUnit) method behavior
 * @run main java.time.DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DurationWitupTest {
    public static void main(String[] args) {
        testGetWithSeconds();
        testGetWithNanos();
        testGetWithUnsupportedUnit();
    }

    private static void testGetWithSeconds() {
        Duration d = Duration.ofSeconds(123, 456);
        long result = d.get(ChronoUnit.SECONDS);
        if (result != 123L) {
            throw new AssertionError("Expected seconds 123 but got " + result);
        }
    }

    private static void testGetWithNanos() {
        Duration d = Duration.ofSeconds(123, 456789);
        long result = d.get(ChronoUnit.NANOS);
        if (result != 456789L) {
            throw new AssertionError("Expected nanos 456789 but got " + result);
        }
    }

    private static void testGetWithUnsupportedUnit() {
        Duration d = Duration.ofSeconds(1);
        try {
            d.get(ChronoUnit.MINUTES); // unsupported unit
            throw new AssertionError("Expected UnsupportedTemporalTypeException for MINUTES unit");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
        try {
            d.get(null); // null unit should also throw
            throw new AssertionError("Expected NullPointerException for null unit");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
