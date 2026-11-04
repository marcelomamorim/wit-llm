/*
 * @test
 * @summary Tests StringLatin1.toUpperCase for various scenarios.
 * @run main StringLatin1WitupTest
 */

import java.util.Locale;
import java.lang.String;
import java.lang.StringLatin1;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testToUpperCaseBasic();
        testToUpperCaseWithTurkishLocale();
        testToUpperCaseWithNullLocale();
        testToUpperCaseWithNonLatin1Characters();
    }

    static void testToUpperCaseBasic() {
        String original = "abc";
        byte[] value = {97, 98, 99}; // 'a', 'b', 'c'
        String result = StringLatin1.toUpperCase(original, value, Locale.ENGLISH);
        assert result.equals("ABC") : "Expected 'ABC', got '" + result + "'";
    }

    static void testToUpperCaseWithTurkishLocale() {
        String original = "i";
        byte[] value = {105}; // 'i'
        Locale trLocale = new Locale("tr");
        String result = StringLatin1.toUpperCase(original, value, trLocale);
        // Turkish locale upper case of 'i' is 'İ' (U+0130), which cannot be Latin1
        // So should fallback to toUpperCaseEx, returning original string
        assert result.equals(original) : "Expected '" + original + "', got '" + result + "'";
    }

    static void testToUpperCaseWithNullLocale() {
        String original = "abc";
        byte[] value = {97, 98, 99};
        try {
            StringLatin1.toUpperCase(original, value, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testToUpperCaseWithNonLatin1Characters() {
        String original = "abc";
        byte[] value = {97, 98, 200}; // 'a', 'b', 'È' (non-Latin1 character)
        String result = StringLatin1.toUpperCase(original, value, Locale.ENGLISH);
        // Should fallback to toUpperCaseEx, returning original string
        assert result.equals(original) : "Expected '" + original + "', got '" + result + "'";
    }
}
