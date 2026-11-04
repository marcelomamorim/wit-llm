/*
 * @test
 * @summary Behavioral tests for Locale.getUnicodeLocaleType(String)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void expectIllegalArgument(Locale locale, String key) {
        boolean thrown = false;
        try {
            locale.getUnicodeLocaleType(key);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for key \"" + key + "\"");
    }

    public static void main(String[] args) {
        Locale plain = new Locale("en", "US");
        check(plain.getUnicodeLocaleType("ca") == null,
              "locale without extensions must return null for a valid key");

        Locale withExt = Locale.forLanguageTag("en-US-u-ca-japanese");
        check("japanese".equals(withExt.getUnicodeLocaleType("ca")),
              "unicode type for ca must be japanese");
        check(withExt.getUnicodeLocaleType("nu") == null,
              "absent unicode key must return null");

        expectIllegalArgument(plain, "");
        expectIllegalArgument(plain, "a");
        expectIllegalArgument(plain, "abc");
        expectIllegalArgument(plain, "c-");
        expectIllegalArgument(withExt, "a");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
