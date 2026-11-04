/*
 * @test
 * @summary Witup-style checks for OffsetDateTime.now(Clock)
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testNullClockThrowsNPE();
        testFixedClockWithOffset();
        testFixedClockUtc();
    }

    static void testNullClockThrowsNPE() {
        try {
            OffsetDateTime.now(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testFixedClockWithOffset() {
        Instant instant = Instant.ofEpochSecond(0L);
        Clock clock = Clock.fixed(instant, ZoneOffset.ofHours(2));
        OffsetDateTime now = OffsetDateTime.now(clock);
        check(now.toInstant().equals(instant), "instant mismatch");
        check(now.getOffset().equals(ZoneOffset.ofHours(2)), "offset mismatch");
        check(now.getYear() == 1970, "year mismatch");
        check(now.getMonthValue() == 1, "month mismatch");
        check(now.getDayOfMonth() == 1, "day mismatch");
        check(now.getHour() == 2, "hour mismatch");
        check(now.getMinute() == 0, "minute mismatch");
        check(now.getSecond() == 0, "second mismatch");
    }

    static void testFixedClockUtc() {
        Instant instant = Instant.ofEpochSecond(1514764800L, 123456789L);
        Clock clock = Clock.fixed(instant, ZoneId.of("UTC"));
        OffsetDateTime now = OffsetDateTime.now(clock);
        check(now.toInstant().equals(instant), "instant mismatch");
        check(now.getOffset().equals(ZoneOffset.UTC), "offset should be UTC");
        check(now.getNano() == 123456789, "nano mismatch");
        check(now.getYear() == 2018, "year mismatch");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
