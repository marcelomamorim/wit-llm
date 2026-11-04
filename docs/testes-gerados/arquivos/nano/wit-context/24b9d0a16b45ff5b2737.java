/*
 * @test
 * @summary Test for Calendar.getWeekYear() method.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testGetWeekYearThrowsException();
    }

    public static void testGetWeekYearThrowsException() {
        Calendar cal = Calendar.getInstance();
        try {
            int weekYear = cal.getWeekYear();
            System.out.println("Unexpected success: getWeekYear() returned " + weekYear);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception: " + t);
        }
    }
}
