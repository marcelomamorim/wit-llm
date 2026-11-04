/*
 * @test
 * @summary Test Locale constructor with all parameters for null handling and basic instantiation
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testLocaleConstructorValid();
        testLocaleConstructorNullLanguage();
        testLocaleConstructorNullCountry();
        testLocaleConstructorNullVariant();
    }

    static void testLocaleConstructorValid() {
        Locale locale = new Locale("en", "US", "POSIX");
        assert locale != null : "Locale should not be null";
        assert "en".equals(locale.getLanguage()) : "Language mismatch";
        assert "US".equals(locale.getCountry()) : "Country mismatch";
        assert "POSIX".equals(locale.getVariant()) : "Variant mismatch";
    }

    static void testLocaleConstructorNullLanguage() {
        try {
            new Locale(null, "US", "POSIX");
            assert false : "Expected NullPointerException for null language";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testLocaleConstructorNullCountry() {
        try {
            new Locale("en", null, "POSIX");
            assert false : "Expected NullPointerException for null country";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testLocaleConstructorNullVariant() {
        try {
            new Locale("en", "US", null);
            assert false : "Expected NullPointerException for null variant";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
