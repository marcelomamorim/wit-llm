/*
 * @test
 * @summary Checks Month.getDisplayName for FULL, SHORT and NARROW styles using Locale.US
 * @run main MonthWitupTest
 */

import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MonthWitupTest {

    public static void main(String[] args) {
        check(Month.JANUARY.getDisplayName(TextStyle.FULL, Locale.US), "January");
        check(Month.JANUARY.getDisplayName(TextStyle.SHORT, Locale.US), "Jan");
        check(Month.DECEMBER.getDisplayName(TextStyle.FULL, Locale.US), "December");
        check(Month.SEPTEMBER.getDisplayName(TextStyle.SHORT, Locale.US), "Sep");
        check(Month.MARCH.getDisplayName(TextStyle.NARROW, Locale.US), "M");

        Set<String> fullNames = new HashSet<>();
        for (Month m : Month.values()) {
            String full = m.getDisplayName(TextStyle.FULL, Locale.US);
            if (full == null || full.isEmpty()) {
                throw new RuntimeException("Empty FULL display name for " + m);
            }
            if (!fullNames.add(full)) {
                throw new RuntimeException("Duplicate FULL display name: " + full);
            }
            String again = m.getDisplayName(TextStyle.FULL, Locale.US);
            check(again, full);
        }
        check(fullNames.size(), 12);

        check(Month.of(7).getDisplayName(TextStyle.FULL, Locale.US), "July");
    }

    private static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }

    private static void check(int actual, int expected) {
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
