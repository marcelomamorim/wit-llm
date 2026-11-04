/*
 * @test
 * @summary Test getDisplayVariant method of Locale class for various locales
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testEmptyVariant();
        testNonEmptyVariant();
        testDifferentLocale();
    }

    private static void testEmptyVariant() {
        Locale locale = new Locale("en", "US", "");
        String result = locale.getDisplayVariant(locale);
        System.out.println("Empty variant: " + result);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string for empty variant, but got: " + result);
        }
    }

    private static void testNonEmptyVariant() {
        Locale locale = new Locale("en", "US", "POSIX");
        String result = locale.getDisplayVariant(locale);
        System.out.println("Non-empty variant: " + result);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty string for variant, but got: " + result);
        }
    }

    private static void testDifferentLocale() {
        Locale locale1 = new Locale("fr", "FR", "");
        Locale locale2 = new Locale("de", "DE", "");
        String result = locale1.getDisplayVariant(locale2);
        System.out.println("Different locale variant: " + result);
        if (result == null) {
            throw new AssertionError("Result should not be null when locales differ");
        }
    }
}
