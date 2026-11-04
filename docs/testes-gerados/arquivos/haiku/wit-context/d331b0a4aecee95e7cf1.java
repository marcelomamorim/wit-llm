/*
 * @test
 * @summary Behavioral checks for Calendar.Builder.setCalendarType (WIT expaths on unknown types, override and null input)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    interface Action {
        void run();
    }

    static void expect(Class<? extends Throwable> expected, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new AssertionError("Expected " + expected.getName() + " but got " + t, t);
        }
        throw new AssertionError("Expected " + expected.getName() + " but no exception was thrown");
    }

    public static void main(String[] args) {
        Calendar.Builder gb = new Calendar.Builder();
        if (gb.setCalendarType("gregorian") != gb) {
            throw new AssertionError("setCalendarType must return the same builder");
        }
        if (gb.setCalendarType("gregorian") != gb) {
            throw new AssertionError("repeated gregorian (mapped to gregory) must be accepted");
        }

        Calendar.Builder iso = new Calendar.Builder();
        if (iso.setCalendarType("iso8601") != iso) {
            throw new AssertionError("iso8601 must be accepted");
        }

        expect(IllegalArgumentException.class,
               () -> new Calendar.Builder().setCalendarType("no-such-calendar-type"));

        expect(IllegalStateException.class, () -> {
            Calendar.Builder b = new Calendar.Builder();
            b.setCalendarType("buddhist");
            b.setCalendarType("japanese");
        });

        expect(IllegalStateException.class, () -> {
            Calendar.Builder b = new Calendar.Builder();
            b.setCalendarType("gregorian");
            b.setCalendarType("buddhist");
        });

        expect(NullPointerException.class,
               () -> new Calendar.Builder().setCalendarType(null));
    }
}
