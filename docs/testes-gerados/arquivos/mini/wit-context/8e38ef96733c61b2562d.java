/*
 * @test
 * @summary Test Calendar.getWeeksInWeekYear throws UnsupportedOperationException
 * @run main CalendarWitupTest
 */

public class CalendarWitupTest {
    public static void main(String[] args) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        try {
            cal.getWeeksInWeekYear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
