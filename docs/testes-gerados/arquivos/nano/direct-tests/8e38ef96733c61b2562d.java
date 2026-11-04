/*
 * @test
 * @summary Test getWeeksInWeekYear() method of Calendar class.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testGetWeeksInWeekYear();
    }

    public static void testGetWeeksInWeekYear() {
        Calendar cal = Calendar.getInstance();
        try {
            int weeks = cal.getWeeksInWeekYear();
            System.out.println("getWeeksInWeekYear() returned: " + weeks);
        } catch (UnsupportedOperationException e) {
            System.out.println("getWeeksInWeekYear() threw UnsupportedOperationException as expected.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            e.printStackTrace();
        }
    }
}
