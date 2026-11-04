/*
 * @test
 * @summary Checks Locale.getDisplayVariant(Locale) for locales with and without a variant
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNoVariantReturnsEmptyString() {
        Locale locale = new Locale("en", "US");
        String result = locale.getDisplayVariant(new Locale("en", "US"));
        check("".equals(result), "expected empty string for locale without variant, got: " + result);
    }

    static void testNoVariantLanguageOnlyReturnsEmptyString() {
        Locale locale = new Locale("fr");
        String result = locale.getDisplayVariant(new Locale("en"));
        check("".equals(result), "expected empty string for language-only locale, got: " + result);
    }

    static void testVariantLocaleReturnsNonEmptyString() {
        Locale locale = new Locale("en", "US", "POSIX");
        String result = locale.getDisplayVariant(new Locale("en", "US"));
        check(result != null, "display variant must not be null");
        check(!result.isEmpty(), "expected non-empty display variant for locale with variant");
    }

    static void testVariantResultIsStableAcrossCalls() {
        Locale locale = new Locale("en", "US", "POSIX");
        Locale in = new Locale("en", "US");
        String first = locale.getDisplayVariant(in);
        String second = locale.getDisplayVariant(in);
        check(first.equals(second), "getDisplayVariant must be deterministic for same inputs");
    }

    public static void main(String[] args) {
        testNoVariantReturnsEmptyString();
        testNoVariantLanguageOnlyReturnsEmptyString();
        testVariantLocaleReturnsNonEmptyString();
        testVariantResultIsStableAcrossCalls();
    }
}
