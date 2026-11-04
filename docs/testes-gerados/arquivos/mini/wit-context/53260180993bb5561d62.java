/*
 * @test
 * @summary Test java.time.ZonedDateTime.now(Clock) method behavior including null argument check
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    private static void testNowWithValidClock() {
        Clock fixedClock = Clock.fixed(Instant.parse("2020-01-01T10:15:30Z"), ZoneId.of("UTC"));
        ZonedDateTime zdt = ZonedDateTime.now(fixedClock);
        if (zdt == null) {
            throw new AssertionError("ZonedDateTime.now returned null for valid clock");
        }
        if (!zdt.toInstant().equals(fixedClock.instant())) {
            throw new AssertionError("ZonedDateTime.now does not match clock instant");
        }
        if (!zdt.getZone().equals(fixedClock.getZone())) {
            throw new AssertionError("ZonedDateTime.now does not match clock zone");
        }
    }

    private static void testNowWithNullClock() {
        try {
            ZonedDateTime.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"clock".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + e.getMessage());
            }
        }
    }
}
