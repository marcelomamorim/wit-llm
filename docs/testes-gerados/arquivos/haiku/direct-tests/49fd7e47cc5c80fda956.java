/*
 * @test
 * @summary OffsetTime.now(Clock) must reject null and derive the time and offset from the clock instant and zone rules
 * @run main OffsetTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullClockThrowsNPE() {
        try {
            OffsetTime.now((Clock) null);
            throw new RuntimeException("expected NullPointerException for null clock");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testUtcFixedClock() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(5 * 3600 + 7, 123), ZoneOffset.UTC);
        OffsetTime actual = OffsetTime.now(clock);
        OffsetTime expected = OffsetTime.of(5, 0, 7, 123, ZoneOffset.UTC);
        check(actual.equals(expected), "UTC clock mismatch: " + actual);
    }

    static void testFixedOffsetZone() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(0), ZoneOffset.ofHours(2));
        OffsetTime actual = OffsetTime.now(clock);
        OffsetTime expected = OffsetTime.of(2, 0, 0, 0, ZoneOffset.ofHours(2));
        check(actual.equals(expected), "fixed offset mismatch: " + actual);
    }

    static void testDayBoundaryWithNegativeOffset() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(-1), ZoneOffset.ofHours(2));
        OffsetTime actual = OffsetTime.now(clock);
        OffsetTime expected = OffsetTime.of(1, 59, 59, 0, ZoneOffset.ofHours(2));
        check(actual.equals(expected), "day boundary mismatch: " + actual);
    }

    static void testRegionZoneOffsetFromRules() {
        ZoneId paris = ZoneId.of("Europe/Paris");
        Clock clock = Clock.fixed(Instant.ofEpochSecond(0), paris);
        OffsetTime actual = OffsetTime.now(clock);
        OffsetTime expected = OffsetTime.of(1, 0, 0, 0, ZoneOffset.ofHours(1));
        check(actual.equals(expected), "region zone mismatch: " + actual);
    }

    public static void main(String[] args) {
        testNullClockThrowsNPE();
        testUtcFixedClock();
        testFixedOffsetZone();
        testDayBoundaryWithNegativeOffset();
        testRegionZoneOffsetFromRules();
    }
}
