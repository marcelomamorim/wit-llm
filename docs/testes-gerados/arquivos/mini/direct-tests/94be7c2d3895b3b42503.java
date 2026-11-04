/*
 * @test
 * @summary Test DayOfWeek.getDisplayName(TextStyle, Locale) for correctness and locale support
 * @run main DayOfWeekWitupTest
 */

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekWitupTest {
    public static void main(String[] args) {
        testDisplayNameBasic();
        testDisplayNameDifferentLocales();
        testDisplayNameAllTextStyles();
    }

    private static void testDisplayNameBasic() {
        DayOfWeek dow = DayOfWeek.MONDAY;
        String name = dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (!"Monday".equals(name)) {
            throw new AssertionError("Expected 'Monday' but got '" + name + "'");
        }
    }

    private static void testDisplayNameDifferentLocales() {
        DayOfWeek dow = DayOfWeek.TUESDAY;
        String nameEn = dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String nameFr = dow.getDisplayName(TextStyle.FULL, Locale.FRENCH);
        if (nameEn.equals(nameFr)) {
            throw new AssertionError("Expected different names for English and French locales but got '" + nameEn + "'");
        }
    }

    private static void testDisplayNameAllTextStyles() {
        DayOfWeek dow = DayOfWeek.WEDNESDAY;
        Locale locale = Locale.ENGLISH;
        for (TextStyle style : TextStyle.values()) {
            String name = dow.getDisplayName(style, locale);
            if (name == null || name.isEmpty()) {
                throw new AssertionError("Display name for style " + style + " is null or empty");
            }
        }
    }
}
