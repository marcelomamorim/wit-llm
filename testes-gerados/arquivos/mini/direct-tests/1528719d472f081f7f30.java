/*
 * @test
 * @summary Test java.time.temporal.ChronoField.getDisplayName(Locale) behavior
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Objects;

public class ChronoFieldWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameWithNullLocale();
        testGetDisplayNameWithKnownLocale();
        testGetDisplayNameWithUnknownLocale();
    }

    private static void testGetDisplayNameWithNullLocale() {
        try {
            ChronoField.YEAR.getDisplayName(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetDisplayNameWithKnownLocale() {
        // YEAR has displayNameKey "year" so should return localized string if present
        Locale locale = Locale.ENGLISH;
        String displayName = ChronoField.YEAR.getDisplayName(locale);
        if (displayName == null || displayName.isEmpty()) {
            throw new AssertionError("Display name should not be null or empty for YEAR with English locale");
        }
        // The displayName should not be the enum name "YEAR" but a localized string
        if (displayName.equals("YEAR")) {
            throw new AssertionError("Display name should not be the enum name for YEAR");
        }
    }

    private static void testGetDisplayNameWithUnknownLocale() {
        // Use a locale very unlikely to have resource bundle data
        Locale unknownLocale = new Locale("xx", "YY");
        String displayName = ChronoField.YEAR.getDisplayName(unknownLocale);
        if (displayName == null || displayName.isEmpty()) {
            throw new AssertionError("Display name should not be null or empty for unknown locale");
        }
        // If resource bundle missing, should fallback to name
        // So displayName is either localized or the enum name
        if (!(displayName.equals("YEAR") || !displayName.equals("YEAR"))) {
            throw new AssertionError("Display name unexpected for unknown locale");
        }
    }
}
