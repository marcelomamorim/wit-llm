/*
 * @test
 * @summary Test OffsetTime.now(Clock) method for correct behavior
 * @run main OffsetTimeWitupTest
 */

import java.time.Clock;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.Instant;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testNowWithSystemUTC();
        testNowWithFixedClock();
        testNowWithOffsetClock();
    }

    static void testNowWithSystemUTC() {
        OffsetTime result = OffsetTime.now(Clock.systemUTC());
        if (result == null) {
            throw new AssertionError("OffsetTime.now(Clock.systemUTC()) returned null");
        }
        // Verify that the offset matches UTC
        if (!result.getOffset().equals(ZoneOffset.UTC)) {
            throw new AssertionError("Expected offset UTC, but got: " + result.getOffset());
        }
        // Verify that the time is close to current system time
        OffsetTime now = OffsetTime.now(Clock.systemUTC());
        long diffSeconds = Math.abs(now.toLocalTime().toSecondOfDay() - result.toLocalTime().toSecondOfDay());
        if (diffSeconds > 1) {
            throw new AssertionError("Time difference too large: " + diffSeconds + " seconds");
        }
    }

    static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2023-01-01T12:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneOffset.ofHours(2));
        OffsetTime result = OffsetTime.now(fixedClock);
        if (result == null) {
            throw new AssertionError("OffsetTime.now(fixedClock) returned null");
        }
        // The time should correspond to the fixed instant in offset +2
        OffsetTime expected = OffsetTime.ofInstant(fixedInstant, ZoneOffset.ofHours(2));
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    static void testNowWithOffsetClock() {
        Instant fixedInstant = Instant.parse("2023-01-01T15:30:00Z");
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(5, 45);
        Clock offsetClock = Clock.offset(Clock.fixed(fixedInstant, ZoneOffset.UTC), offset.getTotalSeconds());
        OffsetTime result = OffsetTime.now(offsetClock);
        if (result == null) {
            throw new AssertionError("OffsetTime.now(offsetClock) returned null");
        }
        OffsetTime expected = OffsetTime.ofInstant(fixedInstant, offset);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
