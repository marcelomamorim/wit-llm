/*
 * @test
 * @summary Tests LocalDate.now(Clock) with fixed clocks in different zones and null-argument handling
 * @run main LocalDateWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalDateWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testUtcZone() {
        Instant instant = Instant.parse("2018-01-01T23:30:00Z");
        Clock clock = Clock.fixed(instant, ZoneOffset.UTC);
        LocalDate date = LocalDate.now(clock);
        check(date.equals(LocalDate.of(2018, 1, 1)), "UTC zone date should be 2018-01-01 but was " + date);
    }

    static void testPositiveOffsetRollsDateForward() {
        Instant instant = Instant.parse("2018-01-01T23:30:00Z");
        Clock clock = Clock.fixed(instant, ZoneOffset.ofHours(2));
        LocalDate date = LocalDate.now(clock);
        check(date.equals(LocalDate.of(2018, 1, 2)), "+02:00 zone date should be 2018-01-02 but was " + date);
    }

    static void testNegativeOffsetRollsDateBackward() {
        Instant instant = Instant.parse("2018-01-02T03:00:00Z");
        Clock clock = Clock.fixed(instant, ZoneOffset.ofHours(-5));
        LocalDate date = LocalDate.now(clock);
        check(date.equals(LocalDate.of(2018, 1, 1)), "-05:00 zone date should be 2018-01-01 but was " + date);
    }

    static void testNamedZoneUsesZoneRules() {
        Instant instant = Instant.parse("2018-06-15T12:00:00Z");
        ZoneId zone = ZoneId.of("UTC");
        Clock clock = Clock.fixed(instant, zone);
        LocalDate date = LocalDate.now(clock);
        check(date.equals(LocalDate.of(2018, 6, 15)), "UTC named zone date should be 2018-06-15 but was " + date);
    }

    static void testNullClockThrowsNPE() {
        boolean thrown = false;
        try {
            LocalDate.now((Clock) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "now(null) should throw NullPointerException");
    }

    public static void main(String[] args) {
        testUtcZone();
        testPositiveOffsetRollsDateForward();
        testNegativeOffsetRollsDateBackward();
        testNamedZoneUsesZoneRules();
        testNullClockThrowsNPE();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
