/*
 * @test
 * @summary Test java.util.Calendar.getWeekYear() throws UnsupportedOperationException
 * @run main CalendarWitupTest
 */

public class CalendarWitupTest {
    public static void main(String[] args) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        try {
            cal.getWeekYear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
