/*
 * @test
 * @summary Witup: Calendar.Builder.setWeekDefinition must reject week parameters outside 1..7 and return the same builder otherwise
 * @run main CalendarWitupTest
 */

import java.util.Calendar;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testInvalidFirstDayOfWeek(0, 1);
        testInvalidFirstDayOfWeek(-1, 1);
        testInvalidFirstDayOfWeek(8, 1);
        testInvalidMinimalDays(1, 0);
        testInvalidMinimalDays(1, -3);
        testInvalidMinimalDays(1, 8);
        testInvalidBoth(0, 0);
        testInvalidBoth(9, 9);
        testValidBoundaries();
        testValidReturnsSameBuilder();
    }

    static void testInvalidFirstDayOfWeek(int first, int minimal) {
        Calendar.Builder b = new Calendar.Builder();
        expectIAE(b, first, minimal);
    }

    static void testInvalidMinimalDays(int first, int minimal) {
        Calendar.Builder b = new Calendar.Builder();
        expectIAE(b, first, minimal);
    }

    static void testInvalidBoth(int first, int minimal) {
        Calendar.Builder b = new Calendar.Builder();
        expectIAE(b, first, minimal);
    }

    static void testValidBoundaries() {
        Calendar.Builder b = new Calendar.Builder();
        Calendar.Builder r1 = b.setWeekDefinition(1, 1);
        if (r1 == null) {
            throw new RuntimeException("setWeekDefinition(1,1) returned null");
        }
        Calendar.Builder r2 = b.setWeekDefinition(7, 7);
        if (r2 == null) {
            throw new RuntimeException("setWeekDefinition(7,7) returned null");
        }
    }

    static void testValidReturnsSameBuilder() {
        Calendar.Builder b = new Calendar.Builder();
        Calendar.Builder r = b.setWeekDefinition(2, 4);
        if (r != b) {
            throw new RuntimeException("setWeekDefinition must return this builder");
        }
    }

    static void expectIAE(Calendar.Builder b, int first, int minimal) {
        try {
            b.setWeekDefinition(first, minimal);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for firstDayOfWeek="
                + first + ", minimalDaysInFirstWeek=" + minimal);
    }
}
