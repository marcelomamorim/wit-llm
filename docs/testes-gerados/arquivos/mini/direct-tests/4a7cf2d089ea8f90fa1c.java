/*
 * @test
 * @summary Test java.lang.StringLatin1.toUpperCase method behavior
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.util.Locale;

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testNullLocaleThrows();
        testNoChangeReturnsOriginalString();
        testLocaleTurkishTriggersToUpperCaseEx();
        testNonLatin1CodePointTriggersToUpperCaseEx();
        testBasicUpperCaseConversion();
    }

    private static void testNullLocaleThrows() {
        byte[] value = { 'a' };
        try {
            StringLatin1.toUpperCase("a", value, null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNoChangeReturnsOriginalString() {
        byte[] value = { 'A', 'B', 'C' };
        String original = "ABC";
        String result = StringLatin1.toUpperCase(original, value, Locale.ENGLISH);
        if (result != original) {
            throw new RuntimeException("Expected original string to be returned when no changes needed");
        }
    }

    private static void testLocaleTurkishTriggersToUpperCaseEx() {
        // 'i' (0x69) uppercases differently in Turkish locale
        byte[] value = { 'i' };
        String original = "i";
        Locale turkish = new Locale("tr");
        String result = StringLatin1.toUpperCase(original, value, turkish);
        if (result.equals(original)) {
            throw new RuntimeException("Expected different string for Turkish locale uppercasing");
        }
    }

    private static void testNonLatin1CodePointTriggersToUpperCaseEx() {
        // Use a byte value that uppercases to a code point > 255 to force fallback
        // Character.toUpperCaseEx(0xDF) == 0x1E9E (LATIN CAPITAL LETTER SHARP S)
        byte[] value = { (byte)0xDF };
        String original = new String(value, java.lang.String.LATIN1);
        String result = StringLatin1.toUpperCase(original, value, Locale.ENGLISH);
        if (result.equals(original)) {
            throw new RuntimeException("Expected different string when upper case code point not latin1");
        }
    }

    private static void testBasicUpperCaseConversion() {
        byte[] value = { 'a', 'b', 'c' };
        String original = "abc";
        String result = StringLatin1.toUpperCase(original, value, Locale.ENGLISH);
        if (!"ABC".equals(result)) {
            throw new RuntimeException("Expected 'ABC' but got '" + result + "'");
        }
    }
}
