/*
 * @test
 * @summary Test java.util.GregorianCalendar.setWeekDate(int,int,int) method
 * @run main GregorianCalendarWitupTest_P229038483b09
 */

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GregorianCalendarWitupTest_P229038483b09 {
    public static void main(String[] args) {
        testValidSetWeekDate();
        testInvalidDayOfWeek();
        testLenientBehavior();
    }

    private static void testValidSetWeekDate() {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        cal.setLenient(false);

        // Set a valid week date: 2020, week 1, Monday (assuming Monday is first day)
        int firstDay = cal.getFirstDayOfWeek();
        int dayOfWeek = firstDay; // valid dayOfWeek
        int weekYear = 2020;
        int weekOfYear = 1;

        cal.setWeekDate(weekYear, weekOfYear, dayOfWeek);

        // After setting, the calendar's week year, week of year and day of week should match
        if (cal.getWeekYear() != weekYear) {
            throw new RuntimeException("Week year mismatch: expected " + weekYear + " but got " + cal.getWeekYear());
        }
        if (cal.get(java.util.Calendar.WEEK_OF_YEAR) != weekOfYear) {
            throw new RuntimeException("Week of year mismatch: expected " + weekOfYear + " but got " + cal.get(java.util.Calendar.WEEK_OF_YEAR));
        }
        if (cal.get(java.util.Calendar.DAY_OF_WEEK) != dayOfWeek) {
            throw new RuntimeException("Day of week mismatch: expected " + dayOfWeek + " but got " + cal.get(java.util.Calendar.DAY_OF_WEEK));
        }
    }

    private static void testInvalidDayOfWeek() {
        GregorianCalendar cal = new GregorianCalendar();

        // dayOfWeek less than SUNDAY (1)
        try {
            cal.setWeekDate(2020, 1, 0);
            throw new RuntimeException("Expected IllegalArgumentException for dayOfWeek=0");
        } catch (IllegalArgumentException expected) {
        }

        // dayOfWeek greater than SATURDAY (7)
        try {
            cal.setWeekDate(2020, 1, 8);
            throw new RuntimeException("Expected IllegalArgumentException for dayOfWeek=8");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testLenientBehavior() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setLenient(true);

        // Provide an out-of-range weekOfYear and dayOfWeek within valid range
        int weekYear = 2020;
        int weekOfYear = 60; // intentionally large
        int dayOfWeek = cal.getFirstDayOfWeek();

        // Should not throw even if weekOfYear is large because lenient is true
        cal.setWeekDate(weekYear, weekOfYear, dayOfWeek);

        // The calendar should have adjusted the date accordingly
        if (cal.getWeekYear() < weekYear) {
            throw new RuntimeException("Lenient setWeekDate produced weekYear less than input");
        }
    }
}
