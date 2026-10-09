/* @test    @summary Test GregorianCalendar.setWeekDate() for valid and invalid inputs    @run main GregorianCalendarWitupTest_P7ca11b7f0208 */

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GregorianCalendarWitupTest_P7ca11b7f0208 {

    public static void main(String[] args) {
        testValidSetWeekDate();
        testInvalidDayOfWeek();
        testInvalidWeekOfYear();
    }

    private static void testValidSetWeekDate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            gc.setWeekDate(2023, 10, GregorianCalendar.MONDAY);
            int year = gc.get(GregorianCalendar.YEAR);
            int week = gc.get(GregorianCalendar.WEEK_OF_YEAR);
            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
            if (year != 2023 || week != 10 || day != GregorianCalendar.MONDAY) {
                throw new AssertionError("Failed: setWeekDate did not set correct date") ;
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid setWeekDate: " + e);
        }
    }

    private static void testInvalidDayOfWeek() {
        GregorianCalendar gc = new GregorianCalendar();
        try {
            gc.setWeekDate(2023, 10, 8); // Invalid day of week
            throw new AssertionError("Expected IllegalArgumentException for invalid dayOfWeek") ;
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid dayOfWeek: " + e);
        }
    }

    private static void testInvalidWeekOfYear() {
        GregorianCalendar gc = new GregorianCalendar();
        try {
            gc.setWeekDate(2023, 0, GregorianCalendar.MONDAY); // Invalid weekOfYear
            // The method does not explicitly throw for weekOfYear out of range, but we test for correctness
            int week = gc.get(GregorianCalendar.WEEK_OF_YEAR);
            if (week != 0) {
                throw new AssertionError("Failed: weekOfYear not set correctly") ;
            }
        } catch (IllegalArgumentException e) {
            // Depending on implementation, may throw
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid weekOfYear: " + e);
        }
    }
}
