/*
 * @test
 * @summary Test java.util.GregorianCalendar.setWeekDate(int,int,int) for valid and invalid dayOfWeek values
 * @run main java.util.GregorianCalendarWitupTest
 */

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GregorianCalendarWitupTest {
    public static void main(String[] args) {
        testInvalidDayOfWeek();
        testValidSetWeekDate();
    }

    private static void testInvalidDayOfWeek() {
        GregorianCalendar cal = new GregorianCalendar();
        int weekYear = 2020;
        int weekOfYear = 10;

        // dayOfWeek < 1 should throw IllegalArgumentException
        try {
            cal.setWeekDate(weekYear, weekOfYear, 0);
            throw new RuntimeException("Expected IllegalArgumentException for dayOfWeek=0");
        } catch (IllegalArgumentException expected) {
        }

        // dayOfWeek > 7 should throw IllegalArgumentException
        try {
            cal.setWeekDate(weekYear, weekOfYear, 8);
            throw new RuntimeException("Expected IllegalArgumentException for dayOfWeek=8");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testValidSetWeekDate() {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        cal.setLenient(false);

        // Use valid dayOfWeek values from 1 (SUNDAY) to 7 (SATURDAY)
        for (int dayOfWeek = GregorianCalendar.SUNDAY; dayOfWeek <= GregorianCalendar.SATURDAY; dayOfWeek++) {
            cal.clear();
            int weekYear = 2021;
            int weekOfYear = 15;
            cal.setWeekDate(weekYear, weekOfYear, dayOfWeek);

            // After setting, verify that the calendar fields are consistent
            int resultWeekYear = cal.getWeekYear();
            int resultWeekOfYear = cal.get(GregorianCalendar.WEEK_OF_YEAR);
            int resultDayOfWeek = cal.get(GregorianCalendar.DAY_OF_WEEK);

            if (resultWeekYear != weekYear) {
                throw new RuntimeException("Week year mismatch: expected " + weekYear + " but got " + resultWeekYear);
            }
            if (resultWeekOfYear != weekOfYear) {
                throw new RuntimeException("Week of year mismatch: expected " + weekOfYear + " but got " + resultWeekOfYear);
            }
            if (resultDayOfWeek != dayOfWeek) {
                throw new RuntimeException("Day of week mismatch: expected " + dayOfWeek + " but got " + resultDayOfWeek);
            }
        }
    }
}
