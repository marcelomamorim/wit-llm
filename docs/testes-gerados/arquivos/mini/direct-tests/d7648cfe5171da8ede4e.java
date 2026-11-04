/*
 * @test
 * @summary Test String.toLowerCase(Locale) behavior
 * @run main StringWitupTest
 */

import java.util.Locale;

public class StringWitupTest {
    public static void main(String[] args) {
        testToLowerCaseWithEnglishLocale();
        testToLowerCaseWithTurkishLocale();
        testToLowerCaseWithNullLocale();
        testToLowerCaseWithEmptyString();
        testToLowerCaseWithNonLatin1String();
    }

    private static void testToLowerCaseWithEnglishLocale() {
        String original = "HELLO WORLD";
        Locale locale = Locale.ENGLISH;
        String lower = original.toLowerCase(locale);
        if (!"hello world".equals(lower)) {
            throw new AssertionError("Expected 'hello world' but got '" + lower + "'");
        }
    }

    private static void testToLowerCaseWithTurkishLocale() {
        // Turkish locale has special casing for 'I'
        String original = "I";
        Locale turkish = new Locale("tr", "TR");
        String lower = original.toLowerCase(turkish);
        // In Turkish locale, uppercase I maps to dotless i (U+0131)
        if (!"\u0131".equals(lower)) {
            throw new AssertionError("Expected '\u0131' but got '" + lower + "'");
        }
    }

    private static void testToLowerCaseWithNullLocale() {
        try {
            String s = "ABC";
            s.toLowerCase(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testToLowerCaseWithEmptyString() {
        String empty = "";
        Locale locale = Locale.ENGLISH;
        String lower = empty.toLowerCase(locale);
        if (!"".equals(lower)) {
            throw new AssertionError("Expected empty string but got '" + lower + "'");
        }
    }

    private static void testToLowerCaseWithNonLatin1String() {
        // Use a string with supplementary characters (non-Latin1)
        String original = "\uD83D\uDE00"; // 😀 (grinning face emoji)
        Locale locale = Locale.ENGLISH;
        String lower = original.toLowerCase(locale);
        // Emoji should be unchanged by toLowerCase
        if (!original.equals(lower)) {
            throw new AssertionError("Expected emoji unchanged but got '" + lower + "'");
        }
    }
}
