/*
 * @test
 * @summary Test java.time.OffsetTime.now(Clock) method behavior
 * @run main OffsetTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
        testNowWithNullClock();
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2020-01-01T10:15:30.123456789Z");
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 30);
        Clock fixedClock = Clock.fixed(fixedInstant, offset);

        OffsetTime result = OffsetTime.now(fixedClock);

        // The result should have the same offset as the clock
        if (!result.getOffset().equals(offset)) {
            throw new AssertionError("OffsetTime offset does not match clock offset");
        }

        // The time should correspond to the instant adjusted by the offset
        // We verify by converting result to Instant and comparing to fixedInstant
        Instant resultInstant = result.atDate(java.time.LocalDate.of(1970,1,1)).toInstant();
        // Because OffsetTime is time-of-day with offset, the date is epoch day, so resultInstant should equal fixedInstant truncated to time-of-day
        // But since OffsetTime does not hold date, we check that the offset and time are consistent
        // Instead, check that the offset seconds and time fields are consistent with fixedInstant and offset

        // Calculate expected local time from fixedInstant and offset
        long expectedEpochSecond = fixedInstant.getEpochSecond() + offset.getTotalSeconds();
        int expectedNano = fixedInstant.getNano();

        int expectedHour = (int)((expectedEpochSecond / 3600) % 24);
        int expectedMinute = (int)((expectedEpochSecond / 60) % 60);
        int expectedSecond = (int)(expectedEpochSecond % 60);

        if (result.getHour() != expectedHour || result.getMinute() != expectedMinute || result.getSecond() != expectedSecond || result.getNano() != expectedNano) {
            throw new AssertionError("OffsetTime time fields do not match expected values from instant and offset");
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock utcClock = Clock.systemUTC();
        OffsetTime result = OffsetTime.now(utcClock);

        if (!result.getOffset().equals(ZoneOffset.UTC)) {
            throw new AssertionError("OffsetTime offset should be UTC for systemUTC clock");
        }

        // The time should be close to current system time
        Instant nowInstant = Instant.now(utcClock);
        int nowSecondOfDay = (int)((nowInstant.getEpochSecond() % 86400 + 86400) % 86400); // normalize
        int resultSecondOfDay = result.toLocalTime().toSecondOfDay();

        int diff = Math.abs(resultSecondOfDay - nowSecondOfDay);
        if (diff > 2) { // allow 2 seconds difference
            throw new AssertionError("OffsetTime time differs from systemUTC clock instant by more than 2 seconds");
        }
    }

    private static void testNowWithNullClock() {
        try {
            OffsetTime.now(null);
            throw new AssertionError("Expected NullPointerException when passing null clock");
        } catch (NullPointerException npe) {
            // expected
            if (!"clock".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message should be 'clock'");
            }
        }
    }
}
