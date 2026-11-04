/*
 * @test
 * @summary Verifies that Calendar.setWeekDate throws UnsupportedOperationException in the base implementation
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    static class StubCalendar extends Calendar {
        StubCalendar() {
            super();
        }

        @Override
        protected void computeTime() {
        }

        @Override
        protected void computeFields() {
        }

        @Override
        public void add(int field, int amount) {
        }

        @Override
        public void roll(int field, boolean up) {
        }
    }

    static void checkUnsupported(int weekYear, int weekOfYear, int dayOfWeek) {
        Calendar cal = new StubCalendar();
        boolean thrown = false;
        try {
            cal.setWeekDate(weekYear, weekOfYear, dayOfWeek);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for args "
                    + weekYear + ", " + weekOfYear + ", " + dayOfWeek);
        }
    }

    public static void main(String[] args) {
        checkUnsupported(2020, 1, 1);
        checkUnsupported(0, 0, 0);
        checkUnsupported(-5, 100, 7);
        checkUnsupported(Integer.MAX_VALUE, Integer.MIN_VALUE, 3);
    }
}
