/*
 * @test
 * @summary Verifies that LocalTime.now(Clock) derives the local time from the supplied clock instant and zone
 * @run main LocalTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        checkEpochUtc();
        checkPositiveOffset();
        checkNegativeOffsetWrapsToPreviousDay();
        checkNanosArePreserved();
        checkSecondsOfDayAfterOneDay();
        checkConsistentWithFixedClock();
        checkNullClockThrowsNpe();
    }

    static void checkEpochUtc() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(0), ZoneOffset.UTC);
        assertEquals(LocalTime.of(0, 0), LocalTime.now(clock), "epoch in UTC");
    }

    static void checkPositiveOffset() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(0), ZoneOffset.ofHours(2));
        assertEquals(LocalTime.of(2, 0), LocalTime.now(clock), "epoch at +02:00");
    }

    static void checkNegativeOffsetWrapsToPreviousDay() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(0), ZoneOffset.ofHours(-5));
        assertEquals(LocalTime.of(19, 0), LocalTime.now(clock), "epoch at -05:00");
    }

    static void checkNanosArePreserved() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(0, 123456789L), ZoneOffset.UTC);
        assertEquals(LocalTime.of(0, 0, 0, 123456789), LocalTime.now(clock), "nano precision");
    }

    static void checkSecondsOfDayAfterOneDay() {
        // 90061 seconds = 1 day, 1 hour, 1 minute, 1 second
        Clock clock = Clock.fixed(Instant.ofEpochSecond(90061L), ZoneOffset.UTC);
        assertEquals(LocalTime.of(1, 1, 1), LocalTime.now(clock), "time wraps after one day");
    }

    static void checkConsistentWithFixedClock() {
        Instant instant = Instant.ofEpochSecond(1500000000L, 42);
        ZoneOffset zone = ZoneOffset.ofHoursMinutes(5, 30);
        Clock clock = Clock.fixed(instant, zone);
        LocalTime expected = LocalTime.ofSecondOfDay(0).plusSeconds(0);
        LocalTime actual = LocalTime.now(clock);
        LocalTime fromInstant = LocalTime.from(instant.atZone(zone));
        assertEquals(fromInstant, actual, "matches ZonedDateTime derived from same instant and zone");
        if (actual.equals(expected) && expected.getNano() != 42) {
            throw new RuntimeException("unexpected equality");
        }
    }

    static void checkNullClockThrowsNpe() {
        try {
            LocalTime.now(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null clock");
    }

    static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(message + ": expected " + expected + " but was " + actual);
        }
    }
}
