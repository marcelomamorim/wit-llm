/*
 * @test
 * @summary Test setWeekDate method of GregorianCalendar for valid and invalid inputs
 * @run main GregorianCalendarWitupTest
 */

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GregorianCalendarWitupTest {

    public static void main(String[] args) {
        testValidSetWeekDate();
        testInvalidDayOfWeek();
    }

    static void testValidSetWeekDate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            gc.setWeekDate(2021, 10, GregorianCalendar.MONDAY);
            int year = gc.get(GregorianCalendar.YEAR);
            int weekOfYear = gc.get(GregorianCalendar.WEEK_OF_YEAR);
            int dayOfWeek = gc.get(GregorianCalendar.DAY_OF_WEEK);
            if (year != 2021 || weekOfYear != 10 || dayOfWeek != GregorianCalendar.MONDAY) {
                throw new AssertionError("setWeekDate did not set the correct date") ;
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid setWeekDate", e);
        }
    }

    static void testInvalidDayOfWeek() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            gc.setWeekDate(2021, 10, 8); // Invalid day of week
            throw new AssertionError("Expected IllegalArgumentException for invalid dayOfWeek") ;
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
