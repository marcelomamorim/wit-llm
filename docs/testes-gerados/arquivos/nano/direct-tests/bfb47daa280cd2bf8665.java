/*
 * @test
 * @summary Test Locale.getDisplayName(Locale) for various locales.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameSameLocale();
        testGetDisplayNameDifferentLocale();
        testGetDisplayNameEmptyLocale();
        testGetDisplayNameNullLocale();
    }

    static void testGetDisplayNameSameLocale() {
        Locale locale = new Locale("en", "US");
        String displayName = locale.getDisplayName(locale);
        System.out.println("Display name (same locale): " + displayName);
        // Expect a localized string for "English (United States)" in en_US locale
        if (displayName == null || displayName.isEmpty()) {
            throw new AssertionError("Expected non-empty display name for same locale");
        }
    }

    static void testGetDisplayNameDifferentLocale() {
        Locale locale = new Locale("fr", "FR");
        Locale inLocale = new Locale("en", "US");
        String displayName = locale.getDisplayName(inLocale);
        System.out.println("Display name (locale in en_US): " + displayName);
        // Expect a string like "French (France)" in en_US locale
        if (displayName == null || displayName.isEmpty()) {
            throw new AssertionError("Expected non-empty display name for different locale");
        }
    }

    static void testGetDisplayNameEmptyLocale() {
        Locale locale = new Locale("");
        String displayName = locale.getDisplayName();
        System.out.println("Display name (empty locale): " + displayName);
        if (displayName == null || displayName.isEmpty()) {
            throw new AssertionError("Expected non-empty display name for empty locale");
        }
    }

    static void testGetDisplayNameNullLocale() {
        try {
            Locale locale = new Locale("en", "US");
            // Passing null should throw NullPointerException
            locale.getDisplayName(null);
            throw new AssertionError("Expected NullPointerException when passing null locale");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
