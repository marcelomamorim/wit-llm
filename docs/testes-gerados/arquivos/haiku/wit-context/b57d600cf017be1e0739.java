/*
 * @test
 * @summary LocalDate.now(Clock) derives the date from the clock instant and zone, and rejects a null clock
 * @run main LocalDateWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testNullClockThrowsNpe();
        testUtcDate();
        testPositiveOffsetCrossesMidnight();
        testNegativeOffsetCrossesMidnight();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullClockThrowsNpe() {
        boolean thrown = false;
        try {
            LocalDate.now((Clock) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "now(null) must throw NullPointerException");
    }

    static void testUtcDate() {
        Instant instant = Instant.parse("2018-09-01T23:30:00Z");
        Clock clock = Clock.fixed(instant, ZoneOffset.UTC);
        LocalDate actual = LocalDate.now(clock);
        check(actual.equals(LocalDate.of(2018, 9, 1)),
              "expected 2018-09-01 but was " + actual);
    }

    static void testPositiveOffsetCrossesMidnight() {
        Instant instant = Instant.parse("2018-09-01T23:30:00Z");
        Clock clock = Clock.fixed(instant, ZoneOffset.ofHours(2));
        LocalDate actual = LocalDate.now(clock);
        check(actual.equals(LocalDate.of(2018, 9, 2)),
              "expected 2018-09-02 in +02:00 but was " + actual);
    }

    static void testNegativeOffsetCrossesMidnight() {
        Instant instant = Instant.parse("2018-09-02T02:00:00Z");
        Clock clock = Clock.fixed(instant, ZoneOffset.ofHours(-5));
        LocalDate actual = LocalDate.now(clock);
        check(actual.equals(LocalDate.of(2018, 9, 1)),
              "expected 2018-09-01 in -05:00 but was " + actual);
    }
}
