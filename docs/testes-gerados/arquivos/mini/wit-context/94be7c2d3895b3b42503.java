/*
 * @test
 * @summary Test DayOfWeek.getDisplayName(TextStyle, Locale) for valid and null arguments
 * @run main DayOfWeekWitupTest
 */

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekWitupTest {
    public static void main(String[] args) {
        testValidDisplayName();
        testNullStyleThrowsNPE();
        testNullLocaleAllowed();
    }

    private static void testValidDisplayName() {
        DayOfWeek dow = DayOfWeek.MONDAY;
        String name = dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("Expected non-empty display name, got: " + name);
        }
    }

    private static void testNullStyleThrowsNPE() {
        DayOfWeek dow = DayOfWeek.TUESDAY;
        try {
            dow.getDisplayName(null, Locale.ENGLISH);
            throw new AssertionError("Expected NullPointerException for null style");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullLocaleAllowed() {
        DayOfWeek dow = DayOfWeek.WEDNESDAY;
        // According to source, locale can be null and should not throw
        String name = dow.getDisplayName(TextStyle.SHORT, null);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("Expected non-empty display name with null locale, got: " + name);
        }
    }
}
