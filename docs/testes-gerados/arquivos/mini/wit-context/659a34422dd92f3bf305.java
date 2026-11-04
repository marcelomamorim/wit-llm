/*
 * @test
 * @summary Test ImmutableGregorianDate.addMonth(int) throws UnsupportedOperationException
 * @run main ImmutableGregorianDateWitupTest
 */

import sun.util.calendar.ImmutableGregorianDate;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;

public class ImmutableGregorianDateWitupTest {
    public static void main(String[] args) {
        // Create a minimal BaseCalendar.Date subclass instance for testing
        BaseCalendar.Date baseDate = new BaseCalendar.Date() {
            @Override
            public int getYear() { return 2020; }
            @Override
            public int getMonth() { return 1; }
            @Override
            public int getDayOfMonth() { return 1; }
            @Override
            public int getDayOfWeek() { return 3; }
            @Override
            public int getHours() { return 0; }
            @Override
            public int getMinutes() { return 0; }
            @Override
            public int getSeconds() { return 0; }
            @Override
            public int getMillis() { return 0; }
            @Override
            public long getTimeOfDay() { return 0L; }
            @Override
            public boolean isLeapYear() { return false; }
            @Override
            public BaseCalendar.Era getEra() { return null; }
            @Override
            public void setLeapYear(boolean leapYear) { }
        };

        ImmutableGregorianDate igd = new ImmutableGregorianDate(baseDate);

        // Test that addMonth throws UnsupportedOperationException
        try {
            igd.addMonth(1);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
