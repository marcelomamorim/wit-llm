/*
 * @test
 * @summary Test java.time.LocalTime.now(Clock) method behavior
 * @run main LocalTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
        testNowWithNullClock();
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2020-01-01T10:15:30.123456789Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneOffset.UTC);
        LocalTime time = LocalTime.now(fixedClock);
        // The time should match the fixed instant converted to LocalTime in UTC
        LocalTime expected = LocalTime.ofInstant(fixedInstant, ZoneOffset.UTC);
        if (!time.equals(expected)) {
            throw new AssertionError("Fixed clock test failed: expected " + expected + " but got " + time);
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock systemUTC = Clock.systemUTC();
        LocalTime time1 = LocalTime.now(systemUTC);
        LocalTime time2 = LocalTime.now(systemUTC);
        // time2 should be equal or after time1 (time flows forward)
        if (time2.isBefore(time1)) {
            throw new AssertionError("System UTC clock test failed: time moved backwards");
        }
    }

    private static void testNowWithNullClock() {
        try {
            LocalTime.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException npe) {
            // expected
            if (!"clock".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
