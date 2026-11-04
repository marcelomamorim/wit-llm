/*
 * @test
 * @summary Checks Locale.getDisplayVariant(Locale) for empty and non-empty variants
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testEmptyVariantReturnsEmptyString();
        testEmptyVariantIgnoresDisplayLocale();
        testNonEmptyVariantReturnsNonEmptyString();
        testNonEmptyVariantIsDeterministic();
    }

    static void testEmptyVariantReturnsEmptyString() {
        Locale locale = new Locale("en", "US");
        String result = locale.getDisplayVariant(Locale.ENGLISH);
        if (result == null) {
            throw new RuntimeException("getDisplayVariant returned null for empty variant");
        }
        if (!result.isEmpty()) {
            throw new RuntimeException("Expected empty string for empty variant, got: " + result);
        }
    }

    static void testEmptyVariantIgnoresDisplayLocale() {
        Locale locale = new Locale("de");
        String a = locale.getDisplayVariant(Locale.GERMAN);
        String b = locale.getDisplayVariant(Locale.FRENCH);
        if (!"".equals(a) || !"".equals(b)) {
            throw new RuntimeException("Expected empty string for empty variant, got: "
                    + "[" + a + "] [" + b + "]");
        }
    }

    static void testNonEmptyVariantReturnsNonEmptyString() {
        Locale locale = new Locale("en", "US", "POSIX");
        String result = locale.getDisplayVariant(Locale.ENGLISH);
        if (result == null) {
            throw new RuntimeException("getDisplayVariant returned null for non-empty variant");
        }
        if (result.isEmpty()) {
            throw new RuntimeException("Expected non-empty display variant for variant POSIX");
        }
    }

    static void testNonEmptyVariantIsDeterministic() {
        Locale locale = new Locale("en", "US", "POSIX");
        String first = locale.getDisplayVariant(Locale.ENGLISH);
        String second = locale.getDisplayVariant(Locale.ENGLISH);
        if (first == null || !first.equals(second)) {
            throw new RuntimeException("getDisplayVariant is not deterministic: "
                    + first + " vs " + second);
        }
    }
}
