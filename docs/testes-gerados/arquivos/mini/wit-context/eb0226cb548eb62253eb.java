/*
 * @test
 * @summary Deterministic tests for java.time.Clock.millis()
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;

public class ClockWitupTest {

    public static void main(String[] args) {
        testMillisReturnsEpochMilli();
        testMillisConsistency();
    }

    private static void testMillisReturnsEpochMilli() {
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(123456789L), java.time.ZoneId.of("UTC"));
        long millis = fixedClock.millis();
        if (millis != 123456789L) {
            throw new AssertionError("Expected millis to be 123456789 but was " + millis);
        }
    }

    private static void testMillisConsistency() {
        Clock fixedClock = Clock.fixed(Instant.ofEpochSecond(1000, 500_000_000), java.time.ZoneId.of("UTC"));
        long expectedMillis = fixedClock.instant().toEpochMilli();
        long millis = fixedClock.millis();
        if (millis != expectedMillis) {
            throw new AssertionError("Millis value " + millis + " does not match instant.toEpochMilli() " + expectedMillis);
        }
    }
}
