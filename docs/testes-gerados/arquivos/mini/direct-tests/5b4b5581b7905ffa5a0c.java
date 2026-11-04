/*
 * @test
 * @summary Test java.time.Duration.of(long, TemporalUnit) method
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class DurationWitupTest {
    public static void main(String[] args) {
        testOfWithSeconds();
        testOfWithNanos();
        testOfWithZero();
        testOfWithNegative();
        testOfWithDays();
    }

    private static void testOfWithSeconds() {
        Duration d = Duration.of(10L, ChronoUnit.SECONDS);
        if (d.getSeconds() != 10L || d.getNano() != 0) {
            throw new AssertionError("Duration.of(10, SECONDS) failed: " + d);
        }
    }

    private static void testOfWithNanos() {
        Duration d = Duration.of(500L, ChronoUnit.NANOS);
        if (d.getSeconds() != 0L || d.getNano() != 500) {
            throw new AssertionError("Duration.of(500, NANOS) failed: " + d);
        }
    }

    private static void testOfWithZero() {
        Duration d = Duration.of(0L, ChronoUnit.MINUTES);
        if (d.getSeconds() != 0L || d.getNano() != 0) {
            throw new AssertionError("Duration.of(0, MINUTES) failed: " + d);
        }
    }

    private static void testOfWithNegative() {
        Duration d = Duration.of(-3L, ChronoUnit.HOURS);
        if (d.getSeconds() != -3L * 3600 || d.getNano() != 0) {
            throw new AssertionError("Duration.of(-3, HOURS) failed: " + d);
        }
    }

    private static void testOfWithDays() {
        Duration d = Duration.of(2L, ChronoUnit.DAYS);
        if (d.getSeconds() != 2L * 86400 || d.getNano() != 0) {
            throw new AssertionError("Duration.of(2, DAYS) failed: " + d);
        }
    }
}