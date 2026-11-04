/*
 * @test
 * @summary Test java.time.LocalTime.ofSecondOfDay(long) method
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testOfSecondOfDayZero();
        testOfSecondOfDayMidnightMinusOne();
        testOfSecondOfDayMidnight();
        testOfSecondOfDayNoon();
        testOfSecondOfDayLastSecond();
    }

    private static void testOfSecondOfDayZero() {
        LocalTime t = LocalTime.ofSecondOfDay(0L);
        if (t.getHour() != 0 || t.getMinute() != 0 || t.getSecond() != 0 || t.getNano() != 0) {
            throw new AssertionError("Expected 00:00:00 but got " + t);
        }
    }

    private static void testOfSecondOfDayMidnightMinusOne() {
        try {
            LocalTime.ofSecondOfDay(-1L);
            throw new AssertionError("Expected DateTimeException for negative secondOfDay");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testOfSecondOfDayMidnight() {
        LocalTime t = LocalTime.ofSecondOfDay(24L * 60 * 60 - 1);
        if (t.getHour() != 23 || t.getMinute() != 59 || t.getSecond() != 59 || t.getNano() != 0) {
            throw new AssertionError("Expected 23:59:59 but got " + t);
        }
    }

    private static void testOfSecondOfDayNoon() {
        LocalTime t = LocalTime.ofSecondOfDay(12L * 60 * 60);
        if (t.getHour() != 12 || t.getMinute() != 0 || t.getSecond() != 0 || t.getNano() != 0) {
            throw new AssertionError("Expected 12:00:00 but got " + t);
        }
    }

    private static void testOfSecondOfDayLastSecond() {
        LocalTime t = LocalTime.ofSecondOfDay(86399L); // last second of day
        if (t.getHour() != 23 || t.getMinute() != 59 || t.getSecond() != 59 || t.getNano() != 0) {
            throw new AssertionError("Expected 23:59:59 but got " + t);
        }
    }
}
