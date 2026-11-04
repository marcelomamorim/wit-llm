/*
 * @test
 * @summary Test the constructor Locale.LanguageRange(String range) for various inputs.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testEmptyRange();
        testNullRange();
        testWhitespaceRange();
        testSpecialCharactersRange();
    }

    static void testValidRange() {
        String range = "en-US";
        Locale.LanguageRange lr = new Locale.LanguageRange(range);
        assert lr != null : "Locale.LanguageRange should not be null for valid input";
        assert lr.getRange().equals(range) : "Range should match input";
    }

    static void testEmptyRange() {
        String range = "";
        Locale.LanguageRange lr = new Locale.LanguageRange(range);
        assert lr != null : "Locale.LanguageRange should not be null for empty string";
        assert lr.getRange().equals(range) : "Range should be empty string";
    }

    static void testNullRange() {
        try {
            new Locale.LanguageRange(null);
            assert false : "Expected NullPointerException for null input";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testWhitespaceRange() {
        String range = "   ";
        Locale.LanguageRange lr = new Locale.LanguageRange(range);
        assert lr != null : "Locale.LanguageRange should not be null for whitespace";
        assert lr.getRange().equals(range) : "Range should match whitespace string";
    }

    static void testSpecialCharactersRange() {
        String range = "en-@#";
        Locale.LanguageRange lr = new Locale.LanguageRange(range);
        assert lr != null : "Locale.LanguageRange should not be null for special characters";
        assert lr.getRange().equals(range) : "Range should match input with special characters";
    }
}
