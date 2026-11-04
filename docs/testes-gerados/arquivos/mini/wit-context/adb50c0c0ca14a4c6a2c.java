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
        testMillisFixedClock();
    }

    private static void testMillisReturnsEpochMilli() {
        // Create a fixed clock at a known instant
        Instant instant = Instant.ofEpochSecond(1000L, 500_000_000L); // 1000.5 seconds
        Clock fixedClock = Clock.fixed(instant, java.time.ZoneOffset.UTC);
        long expected = instant.toEpochMilli();
        long actual = fixedClock.millis();
        if (actual != expected) {
            throw new AssertionError("Expected millis " + expected + " but got " + actual);
        }
    }

    private static void testMillisFixedClock() {
        // Test millis on fixed clock with zero nanos
        Instant instant = Instant.ofEpochSecond(0L, 0);
        Clock fixedClock = Clock.fixed(instant, java.time.ZoneOffset.UTC);
        long expected = 0L;
        long actual = fixedClock.millis();
        if (actual != expected) {
            throw new AssertionError("Expected millis 0 but got " + actual);
        }
    }
}
