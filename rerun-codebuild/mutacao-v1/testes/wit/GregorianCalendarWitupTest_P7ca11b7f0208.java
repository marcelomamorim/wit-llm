/* @test    @summary Test setWeekDate method for valid and invalid inputs    @run main GregorianCalendarWitupTest_P7ca11b7f0208 */

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GregorianCalendarWitupTest_P7ca11b7f0208 {

    public static void main(String[] args) {
        testValidSetWeekDate();
        testInvalidSetWeekDate();
    }

    static void testValidSetWeekDate() {
        GregorianCalendar gc = new GregorianCalendar();
        try {
            gc.setWeekDate(2023, 10, GregorianCalendar.MONDAY);
            int year = gc.get(GregorianCalendar.YEAR);
            int week = gc.get(GregorianCalendar.WEEK_OF_YEAR);
            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
            if (year != 2023 || week != 10 || day != GregorianCalendar.MONDAY) {
                throw new AssertionError("Unexpected calendar state after valid setWeekDate");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during valid setWeekDate: " + e);
        }
    }

    static void testInvalidSetWeekDate() {
        GregorianCalendar gc = new GregorianCalendar();
        boolean thrown = false;
        try {
            gc.setWeekDate(2023, 5, 0); // Invalid dayOfWeek (less than 1)
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for dayOfWeek=0");
        }
        thrown = false;
        try {
            gc.setWeekDate(2023, 5, 8); // Invalid dayOfWeek (greater than 7)
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for dayOfWeek=8");
        }
    }
}
