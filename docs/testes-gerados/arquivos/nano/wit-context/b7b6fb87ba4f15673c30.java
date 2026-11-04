/*
 * @test
 * @summary Test for Duration.get(TemporalUnit)
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        testGetSeconds();
        testGetNanos();
        testGetUnsupportedUnit();
    }

    static void testGetSeconds() {
        Duration duration = Duration.ofSeconds(12345);
        long result = duration.get(ChronoUnit.SECONDS);
        if (result != 12345) {
            throw new AssertionError("Expected 12345, got " + result);
        }
    }

    static void testGetNanos() {
        Duration duration = Duration.ofNanos(987654321);
        long result = duration.get(ChronoUnit.NANOS);
        if (result != 987654321) {
            throw new AssertionError("Expected 987654321, got " + result);
        }
    }

    static void testGetUnsupportedUnit() {
        Duration duration = Duration.ofSeconds(10);
        try {
            duration.get(ChronoUnit.MINUTES);
            throw new AssertionError("Expected UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
