/*
 * @test
 * @summary Test java.time.Month.getDisplayName(TextStyle, Locale) for normal and null style cases
 * @run main MonthWitupTest
 */

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameNormal();
        testGetDisplayNameNullStyle();
    }

    private static void testGetDisplayNameNormal() {
        Month month = Month.JANUARY;
        TextStyle style = TextStyle.FULL;
        Locale locale = Locale.ENGLISH;
        String name = month.getDisplayName(style, locale);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string");
        }
    }

    private static void testGetDisplayNameNullStyle() {
        Month month = Month.JANUARY;
        Locale locale = Locale.ENGLISH;
        try {
            month.getDisplayName(null, locale);
            throw new AssertionError("Expected NullPointerException for null style");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
