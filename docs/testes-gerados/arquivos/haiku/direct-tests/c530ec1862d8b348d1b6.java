/*
 * @test
 * @summary Verifies observable behavior of Clock.offset(Clock, Duration)
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        testZeroOffsetReturnsBaseClock();
        testPositiveOffsetShiftsInstant();
        testNegativeOffsetShiftsInstant();
        testOffsetPreservesZone();
        testOffsetAffectsMillis();
        testNestedOffsetsAccumulate();
        testNullBaseClockThrowsNpe();
        testNullDurationThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroOffsetReturnsBaseClock() {
        Clock fixed = Clock.fixed(Instant.parse("2020-01-01T00:00:00Z"), ZoneOffset.UTC);
        Clock result = Clock.offset(fixed, Duration.ZERO);
        check(result == fixed, "zero offset must return the same base clock instance");
    }

    static void testPositiveOffsetShiftsInstant() {
        Instant base = Instant.parse("2020-01-01T00:00:00Z");
        Clock fixed = Clock.fixed(base, ZoneOffset.UTC);
        Clock shifted = Clock.offset(fixed, Duration.ofHours(1));
        check(shifted.instant().equals(Instant.parse("2020-01-01T01:00:00Z")),
              "positive offset must add duration to instant");
    }

    static void testNegativeOffsetShiftsInstant() {
        Instant base = Instant.parse("2020-01-01T00:00:00Z");
        Clock fixed = Clock.fixed(base, ZoneOffset.UTC);
        Clock shifted = Clock.offset(fixed, Duration.ofSeconds(-90));
        check(shifted.instant().equals(Instant.parse("2019-12-31T23:58:30Z")),
              "negative offset must subtract duration from instant");
    }

    static void testOffsetPreservesZone() {
        ZoneOffset zone = ZoneOffset.ofHours(2);
        Clock fixed = Clock.fixed(Instant.parse("2020-01-01T00:00:00Z"), zone);
        Clock shifted = Clock.offset(fixed, Duration.ofMinutes(30));
        check(zone.equals(shifted.getZone()), "offset clock must keep base clock zone");
    }

    static void testOffsetAffectsMillis() {
        Instant base = Instant.parse("2020-01-01T00:00:00Z");
        Clock fixed = Clock.fixed(base, ZoneOffset.UTC);
        Clock shifted = Clock.offset(fixed, Duration.ofSeconds(5));
        check(shifted.millis() == fixed.millis() + 5000L,
              "millis must reflect the applied offset");
    }

    static void testNestedOffsetsAccumulate() {
        Instant base = Instant.parse("2020-01-01T00:00:00Z");
        Clock fixed = Clock.fixed(base, ZoneOffset.UTC);
        Clock first = Clock.offset(fixed, Duration.ofMinutes(1));
        Clock second = Clock.offset(first, Duration.ofMinutes(2));
        check(second.instant().equals(Instant.parse("2020-01-01T00:03:00Z")),
              "nested offsets must accumulate");
    }

    static void testNullBaseClockThrowsNpe() {
        boolean thrown = false;
        try {
            Clock.offset(null, Duration.ofSeconds(1));
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null baseClock must throw NullPointerException");
    }

    static void testNullDurationThrowsNpe() {
        boolean thrown = false;
        try {
            Clock.offset(Clock.systemUTC(), null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null offsetDuration must throw NullPointerException");
    }
}
