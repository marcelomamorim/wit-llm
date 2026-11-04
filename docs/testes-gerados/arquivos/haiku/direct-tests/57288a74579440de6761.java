/*
 * @test
 * @summary Verifies the public Locale(String, String, String) constructor: null rejection, field retention and legacy ISO language code conversion
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testNullLanguage() {
        try {
            new Locale(null, "US", "");
            check(false, "null language should throw NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullCountry() {
        try {
            new Locale("en", null, "");
            check(false, "null country should throw NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullVariant() {
        try {
            new Locale("en", "US", null);
            check(false, "null variant should throw NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testFieldsRetained() {
        Locale l = new Locale("en", "US", "POSIX");
        check("en".equals(l.getLanguage()), "language should be en");
        check("US".equals(l.getCountry()), "country should be US");
        check("POSIX".equals(l.getVariant()), "variant should be POSIX");
    }

    static void testEmptyVariantEqualsTwoArgForm() {
        Locale a = new Locale("en", "US", "");
        Locale b = new Locale("en", "US");
        check(a.equals(b), "three-arg form with empty variant should equal two-arg form");
        check(a.equals(Locale.US), "new Locale(\"en\", \"US\", \"\") should equal Locale.US");
    }

    static void testEmptyFields() {
        Locale l = new Locale("", "", "");
        check("".equals(l.getLanguage()), "empty language should be retained as empty");
        check("".equals(l.getCountry()), "empty country should be retained as empty");
        check("".equals(l.getVariant()), "empty variant should be retained as empty");
    }

    static void testLegacyIsoLanguageConversion() {
        Locale hebrew = new Locale("iw", "IL", "");
        check("he".equals(hebrew.getLanguage()), "legacy code iw should be converted to he");
        Locale indonesian = new Locale("in", "ID", "");
        check("id".equals(indonesian.getLanguage()), "legacy code in should be converted to id");
    }

    public static void main(String[] args) {
        testNullLanguage();
        testNullCountry();
        testNullVariant();
        testFieldsRetained();
        testEmptyVariantEqualsTwoArgForm();
        testEmptyFields();
        testLegacyIsoLanguageConversion();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
