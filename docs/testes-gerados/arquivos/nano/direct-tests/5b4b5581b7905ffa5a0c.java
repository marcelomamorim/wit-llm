/*
 * @test
 * @summary Test Duration.of(long, TemporalUnit) method for various inputs
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        testOfWithSeconds();
        testOfWithMinutes();
        testOfWithHours();
        testOfWithDays();
        testOfWithNegative();
        testOfWithZero();
    }

    static void testOfWithSeconds() {
        Duration d = Duration.of(10, ChronoUnit.SECONDS);
        assert d.toSeconds() == 10 : "Expected 10 seconds, got " + d.toSeconds();
    }

    static void testOfWithMinutes() {
        Duration d = Duration.of(5, ChronoUnit.MINUTES);
        assert d.toMinutes() == 5 : "Expected 5 minutes, got " + d.toMinutes();
    }

    static void testOfWithHours() {
        Duration d = Duration.of(3, ChronoUnit.HOURS);
        assert d.toHours() == 3 : "Expected 3 hours, got " + d.toHours();
    }

    static void testOfWithDays() {
        Duration d = Duration.of(2, ChronoUnit.DAYS);
        assert d.toDays() == 2 : "Expected 2 days, got " + d.toDays();
    }

    static void testOfWithNegative() {
        Duration d = Duration.of(-15, ChronoUnit.SECONDS);
        assert d.isNegative() : "Duration should be negative";
        assert d.toSeconds() == -15 : "Expected -15 seconds, got " + d.toSeconds();
    }

    static void testOfWithZero() {
        Duration d = Duration.of(0, ChronoUnit.SECONDS);
        assert d.isZero() : "Duration should be zero";
        assert d.toSeconds() == 0 : "Expected 0 seconds, got " + d.toSeconds();
    }
}
