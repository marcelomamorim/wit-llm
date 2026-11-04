/*
 * @test
 * @summary Behavioral tests for GregorianCalendar.setWeekDate using public API only
 * @run main GregorianCalendarWitupTest
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class GregorianCalendarWitupTest {

    public static void main(String[] args) {
        testInvalidDayOfWeekLow();
        testInvalidDayOfWeekHigh();
        testFirstWeekSundayCrossesYearBoundary();
        testSecondWeekSunday();
        testTimeOfDayPreserved();
        System.out.println("PASSED");
    }

    static void testInvalidDayOfWeekLow() {
        GregorianCalendar gc = new GregorianCalendar(Locale.US);
        try {
            gc.setWeekDate(2020, 1, 0);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for dayOfWeek=0");
    }

    static void testInvalidDayOfWeekHigh() {
        GregorianCalendar gc = new GregorianCalendar(Locale.US);
        try {
            gc.setWeekDate(2020, 1, 8);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for dayOfWeek=8");
    }

    static void testFirstWeekSundayCrossesYearBoundary() {
        // US locale: Sunday is first day of week, minimal days in first week = 1.
        // Week 1 of 2020 starts Sunday 2019-12-29.
        GregorianCalendar gc = new GregorianCalendar(Locale.US);
        gc.setWeekDate(2020, 1, Calendar.SUNDAY);
        check(gc.get(Calendar.YEAR) == 2019, "year", gc.get(Calendar.YEAR));
        check(gc.get(Calendar.MONTH) == Calendar.DECEMBER, "month", gc.get(Calendar.MONTH));
        check(gc.get(Calendar.DAY_OF_MONTH) == 29, "day", gc.get(Calendar.DAY_OF_MONTH));
    }

    static void testSecondWeekSunday() {
        // Second Sunday-based week of 2020 is 2020-01-05.
        GregorianCalendar gc = new GregorianCalendar(Locale.US);
        gc.setWeekDate(2020, 2, Calendar.SUNDAY);
        check(gc.get(Calendar.YEAR) == 2020, "year", gc.get(Calendar.YEAR));
        check(gc.get(Calendar.MONTH) == Calendar.JANUARY, "month", gc.get(Calendar.MONTH));
        check(gc.get(Calendar.DAY_OF_MONTH) == 5, "day", gc.get(Calendar.DAY_OF_MONTH));
    }

    static void testTimeOfDayPreserved() {
        GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("GMT"), Locale.US);
        gc.set(2021, Calendar.MARCH, 10, 13, 30, 0);
        gc.set(Calendar.MILLISECOND, 0);
        gc.setWeekDate(2020, 2, Calendar.SUNDAY);
        check(gc.get(Calendar.HOUR_OF_DAY) == 13, "hour", gc.get(Calendar.HOUR_OF_DAY));
        check(gc.get(Calendar.MINUTE) == 30, "minute", gc.get(Calendar.MINUTE));
        check(gc.get(Calendar.YEAR) == 2020, "year", gc.get(Calendar.YEAR));
        check(gc.get(Calendar.MONTH) == Calendar.JANUARY, "month", gc.get(Calendar.MONTH));
        check(gc.get(Calendar.DAY_OF_MONTH) == 5, "day", gc.get(Calendar.DAY_OF_MONTH));
    }

    static void check(boolean cond, String what, int actual) {
        if (!cond) {
            throw new RuntimeException("unexpected " + what + ": " + actual);
        }
    }
}
