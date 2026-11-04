/*
 * @test
 * @summary Test java.time.OffsetDateTime.now(Clock) method behavior
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
        testNowWithNullClock();
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2020-01-01T12:34:56.789Z");
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 30);
        Clock fixedClock = Clock.fixed(fixedInstant, offset);

        OffsetDateTime odt = OffsetDateTime.now(fixedClock);

        // The OffsetDateTime instant must equal fixedInstant
        if (!odt.toInstant().equals(fixedInstant)) {
            throw new AssertionError("OffsetDateTime instant does not match fixed instant");
        }

        // The offset must be the same as the clock's offset
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("OffsetDateTime offset does not match clock offset");
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock utcClock = Clock.systemUTC();
        OffsetDateTime odt = OffsetDateTime.now(utcClock);

        Instant nowInstant = utcClock.instant();

        // The OffsetDateTime instant must be close to nowInstant (allowing small delay)
        long diffNanos = Math.abs(odt.toInstant().toEpochMilli() - nowInstant.toEpochMilli());
        if (diffNanos > 1000) { // allow 1 second tolerance
            throw new AssertionError("OffsetDateTime.now(Clock.systemUTC()) differs from clock.instant() by more than 1 second");
        }

        // The offset must be UTC
        if (!odt.getOffset().equals(ZoneOffset.UTC)) {
            throw new AssertionError("OffsetDateTime offset is not UTC for Clock.systemUTC()");
        }
    }

    private static void testNowWithNullClock() {
        try {
            OffsetDateTime.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException npe) {
            // expected
            if (!"clock".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
