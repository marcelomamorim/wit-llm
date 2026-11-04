/*
 * @test
 * @summary Verifies that Calendar.getWeekYear() throws UnsupportedOperationException in the base implementation and that a concrete subclass overriding it (GregorianCalendar) does not.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testBaseGetWeekYearThrows();
        testGregorianGetWeekYearDoesNotThrow();
    }

    static void testBaseGetWeekYearThrows() {
        Calendar stub = new StubCalendar();
        boolean thrown = false;
        try {
            stub.getWeekYear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "Expected UnsupportedOperationException from base Calendar.getWeekYear()");
    }

    static void testGregorianGetWeekYearDoesNotThrow() {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
        cal.clear();
        cal.set(2012, Calendar.JULY, 15);
        int wy = cal.getWeekYear();
        check(wy == 2012, "Expected week year 2012 for 2012-07-15 but got " + wy);
    }
}
