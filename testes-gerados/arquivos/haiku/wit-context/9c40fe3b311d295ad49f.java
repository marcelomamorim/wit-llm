/*
 * @test
 * @summary Verifies that Calendar.setWeekDate(int, int, int) throws UnsupportedOperationException on the current checkout
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        checkUnsupported(cal, 2018, 1, Calendar.MONDAY);
        checkUnsupported(cal, 1, 1, 1);
        checkUnsupported(cal, 2018, 0, 0);
    }

    private static void checkUnsupported(Calendar cal, int weekYear, int weekOfYear, int dayOfWeek) {
        try {
            cal.setWeekDate(weekYear, weekOfYear, dayOfWeek);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException for setWeekDate("
                + weekYear + ", " + weekOfYear + ", " + dayOfWeek + ")");
    }
}
