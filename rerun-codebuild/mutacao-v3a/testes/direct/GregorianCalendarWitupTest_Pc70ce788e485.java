/* @test    @summary Test setWeekDate method for valid and invalid inputs    @run main GregorianCalendarWitupTest_Pc70ce788e485 */

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GregorianCalendarWitupTest_Pc70ce788e485 {

    public static void main(String[] args) {
        testSetWeekDateValid();
        testSetWeekDateInvalidDayOfWeek();
    }

    static void testSetWeekDateValid() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setLenient(true);
        try {
            cal.setWeekDate(2023, 10, GregorianCalendar.MONDAY);
            System.out.println("Valid setWeekDate: " + cal.getTime());
        } catch (Exception e) {
            System.err.println("Unexpected exception in valid case: " + e);
        }
    }

    static void testSetWeekDateInvalidDayOfWeek() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        boolean exceptionThrown = false;
        try {
            cal.setWeekDate(2023, 10, 8); // Invalid dayOfWeek
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            System.out.println("Caught expected IllegalArgumentException for invalid dayOfWeek");
        }
        if (!exceptionThrown) {
            System.err.println("Failed to throw exception for invalid dayOfWeek");
        }
    }
}
