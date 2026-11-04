/*
 * @test
 * @summary Test java.time.Instant.now(Clock) method behavior
 * @run main InstantWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class InstantWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
        testNowWithNullClock();
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2020-01-01T00:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneOffset.UTC);
        Instant result = Instant.now(fixedClock);
        if (!result.equals(fixedInstant)) {
            throw new AssertionError("Expected " + fixedInstant + " but got " + result);
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock systemUTC = Clock.systemUTC();
        Instant result = Instant.now(systemUTC);
        Instant before = Instant.now();
        Instant after = Instant.now();
        // result should be between before and after
        if (result.isBefore(before) || result.isAfter(after)) {
            throw new AssertionError("Instant.now(Clock.systemUTC()) returned an instant outside expected range: " + result);
        }
    }

    private static void testNowWithNullClock() {
        try {
            Instant.now(null);
            throw new AssertionError("Expected NullPointerException when passing null clock");
        } catch (NullPointerException npe) {
            // expected
            if (!Objects.equals(npe.getMessage(), "clock")) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
