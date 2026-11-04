/*
 * @test
 * @summary Test String.toUpperCase(Locale) behavior
 * @run main StringWitupTest
 */

import java.util.Locale;

public class StringWitupTest {
    public static void main(String[] args) {
        testToUpperCaseWithNullLocale();
        testToUpperCaseWithEnglishLocale();
        testToUpperCaseWithTurkishLocale();
        testToUpperCaseWithEmptyString();
        testToUpperCaseWithNonLatin1String();
    }

    private static void testToUpperCaseWithNullLocale() {
        String s = "abc";
        try {
            s.toUpperCase(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testToUpperCaseWithEnglishLocale() {
        String s = "abc";
        String upper = s.toUpperCase(Locale.ENGLISH);
        if (!"ABC".equals(upper)) {
            throw new RuntimeException("toUpperCase(Locale.ENGLISH) failed: expected ABC but got " + upper);
        }
    }

    private static void testToUpperCaseWithTurkishLocale() {
        String s = "i";
        String upper = s.toUpperCase(new Locale("tr", "TR"));
        // Turkish locale uppercases 'i' to 'İ' (U+0130 LATIN CAPITAL LETTER I WITH DOT ABOVE)
        if (!"İ".equals(upper)) {
            throw new RuntimeException("toUpperCase(Turkish) failed: expected İ but got " + upper);
        }
    }

    private static void testToUpperCaseWithEmptyString() {
        String s = "";
        String upper = s.toUpperCase(Locale.ENGLISH);
        if (!"".equals(upper)) {
            throw new RuntimeException("toUpperCase on empty string should return empty string");
        }
    }

    private static void testToUpperCaseWithNonLatin1String() {
        // Use a string with supplementary characters (non-Latin1) to test UTF16 path
        String s = "\uD835\uDD0A"; // MATHEMATICAL BOLD CAPITAL A (U+1D40A)
        String upper = s.toUpperCase(Locale.ENGLISH);
        // The character is already uppercase, so result should be equal
        if (!s.equals(upper)) {
            throw new RuntimeException("toUpperCase on supplementary uppercase character should be unchanged");
        }
    }
}
