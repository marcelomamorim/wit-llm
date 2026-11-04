/*
 * @test
 * @summary Test java.lang.String.toUpperCase(Locale) for null Locale and normal usage
 * @run main StringWitupTest
 */

import java.lang.String;
import java.util.Locale;

public class StringWitupTest {
    public static void main(String[] args) {
        testToUpperCaseWithValidLocale();
        testToUpperCaseWithNullLocale();
    }

    private static void testToUpperCaseWithValidLocale() {
        String original = "abc";
        Locale locale = Locale.ENGLISH;
        String upper = original.toUpperCase(locale);
        if (!"ABC".equals(upper)) {
            throw new AssertionError("Expected ABC but got: " + upper);
        }
    }

    private static void testToUpperCaseWithNullLocale() {
        String sLatin1 = "abc"; // likely Latin1
        String sUTF16 = "\u0100bc"; // contains non-Latin1 char (Ā)

        // Test with null locale on Latin1 string
        try {
            sLatin1.toUpperCase(null);
            throw new AssertionError("Expected NullPointerException for null locale on Latin1 string");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test with null locale on UTF16 string
        try {
            sUTF16.toUpperCase(null);
            throw new AssertionError("Expected NullPointerException for null locale on UTF16 string");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
