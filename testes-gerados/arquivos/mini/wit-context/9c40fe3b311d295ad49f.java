/*
 * @test
 * @summary Test java.util.Calendar.setWeekDate throws UnsupportedOperationException
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setWeekDate(2020, 1, Calendar.MONDAY);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected exception
        }
    }
}
