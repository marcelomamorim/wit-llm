/*
 * @test
 * @summary Verify DayOfWeek.getDisplayName for FULL, SHORT and NARROW styles in US locale
 * @run main DayOfWeekWitupTest
 */

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekWitupTest {

    private static final DayOfWeek[] DAYS = DayOfWeek.values();

    private static final String[] FULL = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    private static final String[] SHORT = {
        "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    };

    private static final String[] NARROW = {
        "M", "T", "W", "T", "F", "S", "S"
    };

    public static void main(String[] args) {
        checkStyle(TextStyle.FULL, FULL);
        checkStyle(TextStyle.SHORT, SHORT);
        checkStyle(TextStyle.NARROW, NARROW);

        check("Wednesday", DayOfWeek.of(3).getDisplayName(TextStyle.FULL, Locale.US));
        check("Sun", DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.US));
        check("M", DayOfWeek.MONDAY.getDisplayName(TextStyle.NARROW, Locale.US));
    }

    private static void checkStyle(TextStyle style, String[] expected) {
        if (DAYS.length != expected.length) {
            throw new RuntimeException("Unexpected number of days: " + DAYS.length);
        }
        for (int i = 0; i < DAYS.length; i++) {
            check(expected[i], DAYS[i].getDisplayName(style, Locale.US));
        }
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }
}
