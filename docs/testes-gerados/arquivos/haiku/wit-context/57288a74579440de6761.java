/*
 * @test
 * @summary Witup-generated checks for the public Locale(String, String, String) constructor null handling
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        Locale l = new Locale("en", "US", "POSIX");
        check("en".equals(l.getLanguage()), "language should be en");
        check("US".equals(l.getCountry()), "country should be US");
        check("POSIX".equals(l.getVariant()), "variant should be POSIX");

        Locale empty = new Locale("", "", "");
        check(empty.equals(Locale.ROOT), "empty triple should equal Locale.ROOT");

        expectNpe(null, "US", "POSIX");
        expectNpe("en", null, "POSIX");
        expectNpe("en", "US", null);
        expectNpe(null, null, null);
    }

    private static void expectNpe(String language, String country, String variant) {
        try {
            new Locale(language, country, variant);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for language=" + language
                + ", country=" + country + ", variant=" + variant);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
