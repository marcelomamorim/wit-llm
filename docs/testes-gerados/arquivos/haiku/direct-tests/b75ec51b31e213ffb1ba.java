/*
 * @test
 * @summary Locale.Builder.setLanguageTag parses valid tags, returns the builder, and rejects ill-formed tags
 * @run main LocaleWitupTest
 */

import java.util.IllformedLocaleException;
import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testValidTagSetsLanguageAndCountry();
        testReturnsSameBuilder();
        testExtensionTagIsApplied();
        testIllformedTagThrows();
    }

    static void testValidTagSetsLanguageAndCountry() {
        Locale l = new Locale.Builder().setLanguageTag("en-US").build();
        check("en".equals(l.getLanguage()), "language should be en");
        check("US".equals(l.getCountry()), "country should be US");
        check("en-US".equals(l.toLanguageTag()), "toLanguageTag should be en-US");
    }

    static void testReturnsSameBuilder() {
        Locale.Builder b = new Locale.Builder();
        Locale.Builder r = b.setLanguageTag("fr-CA");
        check(r == b, "setLanguageTag should return the same builder");
        check("fr-CA".equals(b.build().toLanguageTag()), "built locale should be fr-CA");
    }

    static void testExtensionTagIsApplied() {
        Locale l = new Locale.Builder().setLanguageTag("de-DE-u-co-phonebk").build();
        check("de".equals(l.getLanguage()), "language should be de");
        check("DE".equals(l.getCountry()), "country should be DE");
        check("phonebk".equals(l.getUnicodeLocaleType("co")), "collation type should be phonebk");
    }

    static void testIllformedTagThrows() {
        boolean thrown = false;
        try {
            new Locale.Builder().setLanguageTag("a");
        } catch (IllformedLocaleException e) {
            thrown = true;
        }
        check(thrown, "ill-formed tag 'a' should throw IllformedLocaleException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
