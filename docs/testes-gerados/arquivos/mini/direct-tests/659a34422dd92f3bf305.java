/*
 * @test
 * @summary Test sun.util.calendar.ImmutableGregorianDate addMonth method behavior
 * @run main ImmutableGregorianDateWitupTest
 */

import sun.util.calendar.BaseCalendar;
import sun.util.calendar.ImmutableGregorianDate;
import sun.util.calendar.CalendarDate;

public class ImmutableGregorianDateWitupTest {

    static class DummyDate extends BaseCalendar.Date {
        private int month;

        DummyDate(int month) {
            this.month = month;
        }

        @Override
        public int getMonth() {
            return month;
        }

        // Implement abstract methods with minimal stubs
        @Override
        public sun.util.calendar.Era getEra() {
            return null;
        }

        @Override
        public int getYear() {
            return 0;
        }

        @Override
        public boolean isLeapYear() {
            return false;
        }

        @Override
        public int getDayOfMonth() {
            return 1;
        }

        @Override
        public int getDayOfWeek() {
            return 1;
        }

        @Override
        public int getHours() {
            return 0;
        }

        @Override
        public int getMinutes() {
            return 0;
        }

        @Override
        public int getSeconds() {
            return 0;
        }

        @Override
        public int getMillis() {
            return 0;
        }

        @Override
        public long getTimeOfDay() {
            return 0L;
        }
    }

    public static void main(String[] args) {
        DummyDate dummy = new DummyDate(5);
        ImmutableGregorianDate igd = new ImmutableGregorianDate(dummy);

        // The addMonth method returns 'this' and calls unsupported(), which throws UnsupportedOperationException
        boolean unsupportedThrown = false;
        try {
            CalendarDate result = igd.addMonth(3);
            if (result != igd) {
                throw new RuntimeException("addMonth did not return this instance");
            }
        } catch (UnsupportedOperationException e) {
            unsupportedThrown = true;
        }

        if (!unsupportedThrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from addMonth");
        }

        // Verify that getMonth delegates to the wrapped date
        if (igd.getMonth() != 5) {
            throw new RuntimeException("getMonth did not delegate correctly");
        }
    }
}
