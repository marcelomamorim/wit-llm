/*
 * @test
 * @summary Test Calendar.setWeekDate throws UnsupportedOperationException
 * @run main CalendarWitupTest
 */

public class CalendarWitupTest {
    public static void main(String[] args) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        try {
            cal.setWeekDate(2020, 1, java.util.Calendar.MONDAY);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
