/*
 * @test
 * @summary Behavioral checks for LocalDateTime.plusMinutes(long), including day, month, year and range boundaries
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void checkEquals(LocalDateTime expected, LocalDateTime actual, String message) {
        check(expected.equals(actual), message + " expected=" + expected + " actual=" + actual);
    }

    static void testZeroMinutesIsIdentity() {
        LocalDateTime base = LocalDateTime.of(2020, 5, 17, 12, 34, 56, 789);
        checkEquals(base, base.plusMinutes(0), "plusMinutes(0) must return an equal value");
    }

    static void testCrossesDayBoundary() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 31, 23, 30);
        checkEquals(LocalDateTime.of(2020, 2, 1, 1, 0), base.plusMinutes(90),
                "23:30 + 90 min must roll over to next day");
    }

    static void testNegativeMinutesCrossesYearBoundary() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 0, 10);
        checkEquals(LocalDateTime.of(2019, 12, 31, 23, 50), base.plusMinutes(-20),
                "negative minutes must roll back across year boundary");
    }

    static void testForwardAcrossNewYear() {
        LocalDateTime base = LocalDateTime.of(2019, 12, 31, 23, 59);
        checkEquals(LocalDateTime.of(2020, 1, 1, 0, 0), base.plusMinutes(1),
                "one minute before new year must reach 2020-01-01T00:00");
    }

    static void testLeapDayReached() {
        LocalDateTime base = LocalDateTime.of(2020, 2, 28, 23, 0);
        checkEquals(LocalDateTime.of(2020, 2, 29, 0, 0), base.plusMinutes(60),
                "leap day 2020-02-29 must be reached by plusMinutes");
    }

    static void testSecondsAndNanosPreserved() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 0, 0, 30, 5);
        LocalDateTime result = base.plusMinutes(1);
        check(result.getYear() == 2020, "year preserved");
        check(result.getMonthValue() == 1, "month preserved");
        check(result.getDayOfMonth() == 1, "day preserved");
        check(result.getHour() == 0, "hour expected 0");
        check(result.getMinute() == 1, "minute expected 1");
        check(result.getSecond() == 30, "second preserved");
        check(result.getNano() == 5, "nano preserved");
    }

    static void testLargePositiveMinutesWithinRange() {
        LocalDateTime base = LocalDateTime.of(2000, 1, 1, 0, 0);
        checkEquals(LocalDateTime.of(2000, 1, 2, 0, 0), base.plusMinutes(1440),
                "1440 minutes must equal exactly one day");
    }

    static void testOverflowPastMaxThrows() {
        boolean thrown = false;
        try {
            LocalDateTime.MAX.plusMinutes(1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "MAX.plusMinutes(1) must throw DateTimeException (year out of range)");
    }

    static void testUnderflowPastMinThrows() {
        boolean thrown = false;
        try {
            LocalDateTime.MIN.plusMinutes(-1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "MIN.plusMinutes(-1) must throw DateTimeException (year out of range)");
    }

    public static void main(String[] args) {
        testZeroMinutesIsIdentity();
        testCrossesDayBoundary();
        testNegativeMinutesCrossesYearBoundary();
        testForwardAcrossNewYear();
        testLeapDayReached();
        testSecondsAndNanosPreserved();
        testLargePositiveMinutesWithinRange();
        testOverflowPastMaxThrows();
        testUnderflowPastMinThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
