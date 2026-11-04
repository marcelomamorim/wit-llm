/*
 * @test
 * @summary Test Duration.get(TemporalUnit) method for supported units.
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        testGetSeconds();
        testGetNanos();
        testGetUnsupportedUnit();
    }

    static void testGetSeconds() {
        Duration duration = Duration.ofSeconds(12345);
        long seconds = duration.get(ChronoUnit.SECONDS);
        if (seconds != 12345) {
            throw new AssertionError("Expected 12345 seconds, but got " + seconds);
        }
    }

    static void testGetNanos() {
        Duration duration = Duration.ofNanos(987654321);
        long nanos = duration.get(ChronoUnit.NANOS);
        if (nanos != 987654321) {
            throw new AssertionError("Expected 987654321 nanos, but got " + nanos);
        }
    }

    static void testGetUnsupportedUnit() {
        Duration duration = Duration.ofSeconds(10);
        try {
            duration.get(ChronoUnit.MINUTES);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for MINUTES");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
