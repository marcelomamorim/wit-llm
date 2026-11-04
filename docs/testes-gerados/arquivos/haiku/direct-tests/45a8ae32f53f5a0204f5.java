/*
 * @test
 * @summary Behavioral checks for LocalDateTime.now(Clock) using fixed clocks
 * @run main LocalDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNullClockThrowsNpe();
        testEpochUtc();
        testNanosAndUtc();
        testNegativeOffsetApplied();
    }

    static void testNullClockThrowsNpe() {
        try {
            LocalDateTime.now((Clock) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null clock");
    }

    static void testEpochUtc() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(0L), ZoneOffset.UTC);
        LocalDateTime actual = LocalDateTime.now(clock);
        LocalDateTime expected = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0);
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void testNanosAndUtc() {
        Clock clock = Clock.fixed(Instant.ofEpochSecond(1500000000L, 123456789L), ZoneOffset.UTC);
        LocalDateTime actual = LocalDateTime.now(clock);
        LocalDateTime expected = LocalDateTime.of(2017, 7, 14, 2, 40, 0, 123456789);
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void testNegativeOffsetApplied() {
        ZoneId zone = ZoneOffset.ofHours(-3);
        Clock clock = Clock.fixed(Instant.ofEpochSecond(0L), zone);
        LocalDateTime actual = LocalDateTime.now(clock);
        LocalDateTime expected = LocalDateTime.of(1969, 12, 31, 21, 0, 0, 0);
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
