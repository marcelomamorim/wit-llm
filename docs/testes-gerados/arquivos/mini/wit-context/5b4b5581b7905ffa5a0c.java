/*
 * @test
 * @summary Test java.time.Duration.of(long, TemporalUnit) for normal and exceptional cases
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class DurationWitupTest {
    public static void main(String[] args) {
        testOfWithValidUnits();
        testOfWithNullUnit();
    }

    private static void testOfWithValidUnits() {
        // Test with zero amount and valid unit DAYS
        Duration d1 = Duration.of(0L, ChronoUnit.DAYS);
        if (!d1.equals(Duration.ZERO)) {
            throw new AssertionError("Duration.of(0, DAYS) should be ZERO");
        }

        // Test with positive amount and valid unit SECONDS
        Duration d2 = Duration.of(10L, ChronoUnit.SECONDS);
        if (d2.getSeconds() != 10L || d2.getNano() != 0) {
            throw new AssertionError("Duration.of(10, SECONDS) incorrect");
        }

        // Test with negative amount and valid unit NANOS
        Duration d3 = Duration.of(-5L, ChronoUnit.NANOS);
        if (d3.getSeconds() != 0L || d3.getNano() != -5) {
            // Note: Duration normalizes nanos to positive and adjusts seconds accordingly
            // So we check that total nanos is -5
            long totalNanos = d3.getSeconds() * 1_000_000_000L + d3.getNano();
            if (totalNanos != -5L) {
                throw new AssertionError("Duration.of(-5, NANOS) incorrect total nanos");
            }
        }
    }

    private static void testOfWithNullUnit() {
        try {
            Duration.of(1L, null);
            throw new AssertionError("Expected NullPointerException for null unit");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
