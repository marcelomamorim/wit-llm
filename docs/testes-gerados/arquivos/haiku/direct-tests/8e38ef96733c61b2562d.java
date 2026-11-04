/*
 * @test
 * @summary Verifies Calendar.getWeeksInWeekYear default behavior and GregorianCalendar override
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarWitupTest {

    static class MinimalCalendar extends Calendar {
        MinimalCalendar() {
            super();
        }

        @Override
        public void add(int field, int amount) {
        }

        @Override
        public void roll(int field, boolean up) {
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

        @Override
        protected void computeTime() {
        }

        @Override
        protected void computeFields() {
        }
    }

    public static void main(String[] args) {
        testBaseCalendarThrowsUnsupported();
        testGregorianWeeksInWeekYearIsValid();
    }

    static void testBaseCalendarThrowsUnsupported() {
        Calendar cal = new MinimalCalendar();
        try {
            cal.getWeeksInWeekYear();
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testGregorianWeeksInWeekYearIsValid() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        int weeks = cal.getWeeksInWeekYear();
        if (weeks != 52 && weeks != 53) {
            throw new RuntimeException("Unexpected weeks in week year: " + weeks);
        }
    }
}
