/*
 * @test
 * @summary Witup-generated tests for DayOfWeek.getDisplayName display names and null argument handling
 * @run main DayOfWeekWitupTest
 */

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekWitupTest {

    interface Action {
        void run();
    }

    public static void main(String[] args) {
        check(DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.US), "Monday");
        check(DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.US), "Mon");
        check(DayOfWeek.SUNDAY.getDisplayName(TextStyle.FULL, Locale.US), "Sunday");

        expectNpe(() -> DayOfWeek.MONDAY.getDisplayName(null, Locale.US));
        expectNpe(() -> DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, null));
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }

    static void expectNpe(Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }
}
