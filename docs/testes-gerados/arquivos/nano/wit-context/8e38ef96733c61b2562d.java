/*
 * @test    @summary Test for Calendar.getWeeksInWeekYear() method
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testGetWeeksInWeekYearThrowsException();
    }

    public static void testGetWeeksInWeekYearThrowsException() {
        Calendar cal = Calendar.getInstance();
        try {
            int weeks = cal.getWeeksInWeekYear();
            System.out.println("FAIL: Expected UnsupportedOperationException, but got value: " + weeks);
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
