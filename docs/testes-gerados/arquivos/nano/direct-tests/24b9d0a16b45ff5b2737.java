/*
 * @test    @summary Test for Calendar.getWeekYear() method.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testGetWeekYearThrows();
    }

    private static void testGetWeekYearThrows() {
        Calendar cal = Calendar.getInstance();
        boolean threw = false;
        try {
            cal.getWeekYear();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
