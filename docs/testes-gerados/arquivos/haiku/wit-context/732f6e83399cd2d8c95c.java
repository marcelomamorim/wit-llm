/*
 * @test
 * @summary Witup-generated checks for GregorianCalendar.setWeekDate
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
        testValidWeekDateSundayWeekOne();
        testValidWeekDateMondayWeekTwo();
        testNonLenientInvalidWeek();
    }

    static GregorianCalendar newCal() {
        return new GregorianCalendar(TimeZone.getTimeZone("GMT"), Locale.US);
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testInvalidDayOfWeekLow() {
        GregorianCalendar gc = newCal();
        try {
            gc.setWeekDate(2024, 1, 0);
            fail("expected IllegalArgumentException for dayOfWeek=0");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testInvalidDayOfWeekHigh() {
        GregorianCalendar gc = newCal();
        try {
            gc.setWeekDate(2024, 1, 8);
            fail("expected IllegalArgumentException for dayOfWeek=8");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testValidWeekDateSundayWeekOne() {
        GregorianCalendar gc = newCal();
        gc.setWeekDate(2024, 1, Calendar.SUNDAY);
        if (gc.get(Calendar.YEAR) != 2023
                || gc.get(Calendar.MONTH) != Calendar.DECEMBER
                || gc.get(Calendar.DAY_OF_MONTH) != 31) {
            fail("unexpected date for week 1 Sunday: " + gc.getTime());
        }
    }

    static void testValidWeekDateMondayWeekTwo() {
        GregorianCalendar gc = newCal();
        gc.setWeekDate(2024, 2, Calendar.MONDAY);
        if (gc.get(Calendar.YEAR) != 2024
                || gc.get(Calendar.MONTH) != Calendar.JANUARY
                || gc.get(Calendar.DAY_OF_MONTH) != 8) {
            fail("unexpected date for week 2 Monday: " + gc.getTime());
        }
    }

    static void testNonLenientInvalidWeek() {
        GregorianCalendar gc = newCal();
        gc.setLenient(false);
        try {
            gc.setWeekDate(2024, 60, Calendar.MONDAY);
            fail("expected IllegalArgumentException in non-lenient mode");
        } catch (IllegalArgumentException expected) {
        }
    }
}
