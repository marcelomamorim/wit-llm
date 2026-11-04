/*
 * @test
 * @summary Test java.time.ZonedDateTime.now(Clock) method behavior
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
        testNowWithNullClock();
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2020-01-01T12:00:00Z");
        ZoneId zone = ZoneId.of("Europe/Paris");
        Clock fixedClock = Clock.fixed(fixedInstant, zone);

        ZonedDateTime zdt = ZonedDateTime.now(fixedClock);

        // The ZonedDateTime instant must equal the fixed instant
        if (!zdt.toInstant().equals(fixedInstant)) {
            throw new AssertionError("ZonedDateTime instant does not match fixed clock instant");
        }

        // The zone must be the same as the clock's zone
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("ZonedDateTime zone does not match clock zone");
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock utcClock = Clock.systemUTC();
        ZonedDateTime zdt = ZonedDateTime.now(utcClock);

        Instant nowInstant = Instant.now(utcClock);

        // The ZonedDateTime instant must be close to the current instant (allowing small delay)
        long diff = Math.abs(zdt.toInstant().toEpochMilli() - nowInstant.toEpochMilli());
        if (diff > 1000) { // allow up to 1 second difference
            throw new AssertionError("ZonedDateTime instant differs from clock instant by more than 1 second");
        }

        // The zone must be UTC
        if (!zdt.getZone().equals(ZoneId.of("UTC"))) {
            throw new AssertionError("ZonedDateTime zone is not UTC as expected");
        }
    }

    private static void testNowWithNullClock() {
        try {
            ZonedDateTime.now(null);
            throw new AssertionError("Expected NullPointerException when clock is null");
        } catch (NullPointerException npe) {
            // expected
            if (!"clock".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }
}
