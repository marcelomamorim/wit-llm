/*
 * @test
 * @summary Locale.Builder.removeUnicodeLocaleAttribute removes only the requested attribute, is a no-op for absent attributes, and rejects null or ill-formed input
 * @run main LocaleWitupTest
 */

import java.util.IllformedLocaleException;
import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testRemovesExistingAttributeOnly();
        testRemoveAbsentAttributeIsNoop();
        testRemoveOnlyAttributeLeavesBaseTag();
        testNullAttributeThrowsNPE();
        testIllformedAttributeThrowsIllformedLocale();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemovesExistingAttributeOnly() {
        Locale locale = new Locale.Builder()
                .setLanguageTag("en-US-u-attr-ca-gregory")
                .removeUnicodeLocaleAttribute("attr")
                .build();
        check("en-US-u-ca-gregory".equals(locale.toLanguageTag()),
              "expected en-US-u-ca-gregory but was " + locale.toLanguageTag());
        check("gregory".equals(locale.getUnicodeLocaleType("ca")),
              "keyword ca should be preserved");
    }

    static void testRemoveAbsentAttributeIsNoop() {
        Locale locale = new Locale.Builder()
                .setLanguageTag("en-US")
                .removeUnicodeLocaleAttribute("foo")
                .build();
        check("en-US".equals(locale.toLanguageTag()),
              "expected en-US but was " + locale.toLanguageTag());
    }

    static void testRemoveOnlyAttributeLeavesBaseTag() {
        Locale locale = new Locale.Builder()
                .setLanguageTag("en-US-u-attr")
                .removeUnicodeLocaleAttribute("attr")
                .build();
        check("en-US".equals(locale.toLanguageTag()),
              "expected en-US but was " + locale.toLanguageTag());
    }

    static void testNullAttributeThrowsNPE() {
        Locale.Builder builder = new Locale.Builder();
        try {
            builder.removeUnicodeLocaleAttribute(null);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testIllformedAttributeThrowsIllformedLocale() {
        Locale.Builder builder = new Locale.Builder().setLanguageTag("en-US");
        try {
            builder.removeUnicodeLocaleAttribute("ab");
            throw new RuntimeException("IllformedLocaleException expected");
        } catch (IllformedLocaleException expected) {
            // expected
        }
    }
}
