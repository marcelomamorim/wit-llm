/*
 * @test
 * @summary Test java.time.Instant.now(Clock clock) method for correct behavior.
 * @run main InstantWitupTest
 */

import java.time.Clock;
import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testNowWithSystemUTC();
        testNowWithFixedClock();
        testNowWithOffsetClock();
    }

    static void testNowWithSystemUTC() {
        Clock clock = Clock.systemUTC();
        Instant instant = Instant.now(clock);
        if (instant == null) {
            throw new AssertionError("Instant.now(clock) returned null");
        }
        System.out.println("testNowWithSystemUTC passed: " + instant);
    }

    static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2023-01-01T00:00:00Z");
        Clock clock = Clock.fixed(fixedInstant, java.time.ZoneOffset.UTC);
        Instant instant = Instant.now(clock);
        if (!instant.equals(fixedInstant)) {
            throw new AssertionError("Expected " + fixedInstant + " but got " + instant);
        }
        System.out.println("testNowWithFixedClock passed: " + instant);
    }

    static void testNowWithOffsetClock() {
        Instant baseInstant = Instant.parse("2023-01-01T00:00:00Z");
        Clock clock = Clock.offset(Clock.fixed(baseInstant, java.time.ZoneOffset.UTC), java.time.Duration.ofHours(5));
        Instant instant = Instant.now(clock);
        Instant expected = baseInstant.plusSeconds(5 * 3600);
        if (!instant.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + instant);
        }
        System.out.println("testNowWithOffsetClock passed: " + instant);
    }
}
