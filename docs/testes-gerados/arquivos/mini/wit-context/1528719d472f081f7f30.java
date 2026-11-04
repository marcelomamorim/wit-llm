/*
 * @test
 * @summary Test java.time.temporal.ChronoField.getDisplayName(Locale) for null locale and valid locale
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Objects;

public class ChronoFieldWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameWithNullLocale();
        testGetDisplayNameWithValidLocale();
    }

    private static void testGetDisplayNameWithNullLocale() {
        try {
            ChronoField.DAY_OF_MONTH.getDisplayName(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayNameWithValidLocale() {
        Locale locale = Locale.ENGLISH;
        String name = ChronoField.DAY_OF_MONTH.getDisplayName(locale);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("Display name should not be null or empty for valid locale");
        }
        // The returned name should be either the localized name or the enum name
        if (!name.equals(ChronoField.DAY_OF_MONTH.name()) && name.trim().isEmpty()) {
            throw new AssertionError("Display name is empty or invalid");
        }
    }
}
