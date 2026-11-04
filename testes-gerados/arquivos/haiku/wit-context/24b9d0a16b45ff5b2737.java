/*
 * @test
 * @summary Verify that the base Calendar.getWeekYear() throws UnsupportedOperationException
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarWitupTest {

    static class StubCalendar extends Calendar {
        StubCalendar() {
            super(TimeZone.getTimeZone("UTC"), Locale.US);
        }

        @Override
        public void add(int field, int amount) {
        }

        @Override
        public void roll(int field, boolean up) {
        }

        @Override
        protected void computeTime() {
        }

        @Override
        protected void computeFields() {
        }

        @Override
        public int getMinimum(int field) {
            return 0;
        }

        @Override
        public int getMaximum(int field) {
            return 0;
        }

        @Override
        public int getGreatestMinimum(int field) {
            return 0;
        }

        @Override
        public int getLeastMaximum(int field) {
            return 0;
        }
    }

    public static void main(String[] args) {
        StubCalendar calendar = new StubCalendar();
        boolean thrown = false;
        try {
            calendar.getWeekYear();
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from Calendar.getWeekYear()");
        }
    }
}
