/*
 * @test
 * @summary Basic observable behavior of Locale.getDisplayName(Locale)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testEmptyLocaleReturnsEmptyString();
        testLanguageAndCountryDisplayName();
        testLanguageOnlyDisplayName();
        testVariantOnlyLocaleReturnsNonEmptyName();
    }

    static void testEmptyLocaleReturnsEmptyString() {
        String name = new Locale("").getDisplayName(Locale.US);
        check(name.equals(""), "empty locale should yield empty display name, got: " + name);
    }

    static void testLanguageAndCountryDisplayName() {
        String name = new Locale("en", "US").getDisplayName(Locale.ENGLISH);
        check(name.contains("English"), "expected language name in: " + name);
        check(name.contains("United States"), "expected country name in: " + name);
    }

    static void testLanguageOnlyDisplayName() {
        String name = new Locale("fr").getDisplayName(Locale.ENGLISH);
        check(name.contains("French"), "expected French in: " + name);
    }

    static void testVariantOnlyLocaleReturnsNonEmptyName() {
        String name = new Locale("", "", "POSIX").getDisplayName(Locale.US);
        check(!name.isEmpty(), "variant-only locale should produce a non-empty display name");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
