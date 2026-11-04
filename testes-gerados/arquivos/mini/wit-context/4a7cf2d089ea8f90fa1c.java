/*
 * @test
 * @summary Test StringLatin1.toUpperCase behavior including null locale and normal cases
 * @run main java.lang.StringLatin1WitupTest
 */

import java.util.Locale;

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testNullLocaleThrows();
        testNoChangeReturnsSameString();
        testSimpleUpperCaseConversion();
        testLocaleSpecialCases();
    }

    private static void testNullLocaleThrows() {
        byte[] value = { 'a', 'b', 'c' };
        String str = new String(value, java.lang.String.LATIN1);
        try {
            java.lang.StringLatin1.toUpperCase(str, value, null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNoChangeReturnsSameString() {
        byte[] value = { 'A', 'B', 'C' };
        String str = new String(value, java.lang.String.LATIN1);
        String result = java.lang.StringLatin1.toUpperCase(str, value, Locale.ENGLISH);
        if (result != str) {
            throw new RuntimeException("Expected same string instance when no changes needed");
        }
    }

    private static void testSimpleUpperCaseConversion() {
        byte[] value = { 'a', 'b', 'c' };
        String str = new String(value, java.lang.String.LATIN1);
        String result = java.lang.StringLatin1.toUpperCase(str, value, Locale.ENGLISH);
        if (result.equals(str)) {
            throw new RuntimeException("Expected different string instance when changes needed");
        }
        // Check that result is uppercase ASCII
        for (int i = 0; i < result.length(); i++) {
            char c = result.charAt(i);
            if (c < 'A' || c > 'Z') {
                throw new RuntimeException("Expected uppercase ASCII characters, found: " + c);
            }
        }
    }

    private static void testLocaleSpecialCases() {
        // Test Turkish locale triggers toUpperCaseEx path
        byte[] value = { 'i' };
        String str = new String(value, java.lang.String.LATIN1);
        Locale turkish = new Locale("tr");
        String result = java.lang.StringLatin1.toUpperCase(str, value, turkish);
        if (result.equals(str)) {
            throw new RuntimeException("Expected different string instance for Turkish locale");
        }
        // The uppercase of 'i' in Turkish is not 'I' but 'İ' (U+0130), which is not Latin1,
        // so the method should delegate to toUpperCaseEx and produce a different string.
    }
}
