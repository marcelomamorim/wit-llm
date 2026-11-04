/*
 * @test
 * @summary Test for Locale constructor with null parameters
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testLocaleConstructorWithNullCountry();
        testLocaleConstructorWithNullLanguage();
        testLocaleConstructorWithNullVariant();
    }

    private static void testLocaleConstructorWithNullCountry() {
        try {
            new Locale("en", null, "VAR");
            System.out.println("FAIL: Expected NullPointerException for null country");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null country");
        }
    }

    private static void testLocaleConstructorWithNullLanguage() {
        try {
            new Locale(null, "US", "VAR");
            System.out.println("FAIL: Expected NullPointerException for null language");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null language");
        }
    }

    private static void testLocaleConstructorWithNullVariant() {
        try {
            new Locale("en", "US", null);
            System.out.println("FAIL: Expected NullPointerException for null variant");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null variant");
        }
    }
}
