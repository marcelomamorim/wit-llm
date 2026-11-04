/*
 * @test
 * @summary WITUP-derived checks for Locale.getUnicodeLocaleType argument validation and lookup
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        checkIllegalKey("");
        checkIllegalKey("a");
        checkIllegalKey("abc");

        if (Locale.US.getUnicodeLocaleType("ca") != null) {
            throw new RuntimeException("expected null for locale without extensions");
        }

        Locale l = Locale.forLanguageTag("en-US-u-ca-japanese");
        if (!"japanese".equals(l.getUnicodeLocaleType("ca"))) {
            throw new RuntimeException("expected 'japanese' for key 'ca'");
        }
        if (l.getUnicodeLocaleType("nu") != null) {
            throw new RuntimeException("expected null for absent key 'nu'");
        }
    }

    static void checkIllegalKey(String key) {
        try {
            Locale.US.getUnicodeLocaleType(key);
            throw new RuntimeException("expected IllegalArgumentException for key: '" + key + "'");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
