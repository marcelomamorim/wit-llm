/*
 * @test
 * @summary Test java.time.LocalDateTime.now(Clock) method behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZoneRules;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
        testNowWithNullClock();
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.ofEpochSecond(1000L, 123456789);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        Clock fixedClock = new Clock() {
            @Override
            public ZoneId getZone() {
                return offset;
            }

            @Override
            public Clock withZone(ZoneId zone) {
                return this; // ignore
            }

            @Override
            public Instant instant() {
                return fixedInstant;
            }
        };

        LocalDateTime ldt = LocalDateTime.now(fixedClock);
        // The returned LocalDateTime should correspond to the epoch second and nano with offset applied
        long expectedEpochSecond = fixedInstant.getEpochSecond();
        int expectedNano = fixedInstant.getNano();

        // We cannot directly get epochSecond from LocalDateTime, so convert back to epochSecond with offset
        long actualEpochSecond = ldt.toEpochSecond(offset);
        int actualNano = ldt.getNano();

        if (actualEpochSecond != expectedEpochSecond) {
            throw new AssertionError("Epoch second mismatch: expected " + expectedEpochSecond + " but got " + actualEpochSecond);
        }
        if (actualNano != expectedNano) {
            throw new AssertionError("Nano mismatch: expected " + expectedNano + " but got " + actualNano);
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock utcClock = Clock.systemUTC();
        LocalDateTime ldt = LocalDateTime.now(utcClock);
        Instant nowInstant = utcClock.instant();
        ZoneOffset offset = ZoneOffset.UTC;

        long expectedEpochSecond = nowInstant.getEpochSecond();
        int expectedNano = nowInstant.getNano();

        long actualEpochSecond = ldt.toEpochSecond(offset);
        int actualNano = ldt.getNano();

        if (Math.abs(actualEpochSecond - expectedEpochSecond) > 1) {
            throw new AssertionError("Epoch second mismatch too large: expected " + expectedEpochSecond + " but got " + actualEpochSecond);
        }
        if (actualNano < 0 || actualNano >= 1_000_000_000) {
            throw new AssertionError("Nano out of range: " + actualNano);
        }
    }

    private static void testNowWithNullClock() {
        try {
            LocalDateTime.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException npe) {
            // expected
            if (!"clock".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
