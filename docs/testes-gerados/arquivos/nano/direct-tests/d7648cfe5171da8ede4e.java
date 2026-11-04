/* @test    @summary Test String.toLowerCase(Locale) method for different locales    @run main StringWitupTest */

import java.lang.String;
import java.util.Locale;

public class StringWitupTest {

    public static void main(String[] args) {
        testToLowerCaseDefaultLocale();
        testToLowerCaseEnglishLocale();
        testToLowerCaseTurkishLocale();
    }

    static void testToLowerCaseDefaultLocale() {
        String original = "TeStInG";
        String result = original.toLowerCase(Locale.getDefault());
        String expected = original.toLowerCase(Locale.ENGLISH); // fallback for deterministic test
        if (!result.equals(expected)) {
            throw new AssertionError("Default locale toLowerCase failed: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testToLowerCaseEnglishLocale() {
        String original = "TeStInG";
        String result = original.toLowerCase(Locale.ENGLISH);
        String expected = "testing";
        if (!result.equals(expected)) {
            throw new AssertionError("English locale toLowerCase failed: expected '" + expected + "', got '" + result + "'");
        }
    }

    static void testToLowerCaseTurkishLocale() {
        String original = "Iİ"; // Turkish dotted and dotless I
        String result = original.toLowerCase(new Locale("tr", "TR"));
        String expected = "ıi"; // expected lowercase in Turkish
        if (!result.equals(expected)) {
            throw new AssertionError("Turkish locale toLowerCase failed: expected '" + expected + "', got '" + result + "'");
        }
    }
}
