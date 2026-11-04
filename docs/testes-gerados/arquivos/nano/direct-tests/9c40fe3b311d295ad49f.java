/*
 * @test
 * @summary Test Calendar.setWeekDate method for supported behavior.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetWeekDateBasic();
        testSetWeekDateUnsupported();
    }

    private static void testSetWeekDateBasic() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setWeekDate(2023, 10, Calendar.MONDAY);
            System.out.println("setWeekDate executed without exception.");
        } catch (UnsupportedOperationException e) {
            System.out.println("setWeekDate threw UnsupportedOperationException unexpectedly.");
        } catch (Exception e) {
            System.out.println("setWeekDate threw unexpected exception: " + e);
        }
    }

    private static void testSetWeekDateUnsupported() {
        Calendar cal = Calendar.getInstance();
        boolean exceptionThrown = false;
        try {
            cal.setWeekDate(2023, 15, Calendar.SUNDAY);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
        if (exceptionThrown) {
            System.out.println("setWeekDate correctly threw UnsupportedOperationException.");
        } else {
            System.out.println("setWeekDate did not throw exception as expected.");
        }
    }
}
