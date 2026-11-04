/*
 * @test
 * @summary Tests for StringLatin1.toUpperCase to verify behavior and exception handling.
 * @run main StringLatin1WitupTest
 */

import java.util.Locale;
import java.lang.String;
import java.lang.StringLatin1;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testToUpperCase_NullLocale();
        testToUpperCase_Normal();
        testToUpperCase_AllLatin1();
    }

    private static void testToUpperCase_NullLocale() {
        String str = "test";
        byte[] value = str.getBytes();
        try {
            String result = StringLatin1.toUpperCase(str, value, null);
            // Expect NullPointerException
            System.err.println("Expected NullPointerException for null locale, but got: " + result);
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testToUpperCase_Normal() {
        String str = "abc";
        byte[] value = str.getBytes();
        Locale locale = Locale.ENGLISH;
        String result = StringLatin1.toUpperCase(str, value, locale);
        if (!"ABC".equals(result)) {
            System.err.println("Unexpected result: " + result);
        }
    }

    private static void testToUpperCase_AllLatin1() {
        String str = "\u00e0\u00e1"; // Latin-1 characters
        byte[] value = str.getBytes();
        Locale locale = Locale.ENGLISH;
        String result = StringLatin1.toUpperCase(str, value, locale);
        // Should be uppercase Latin-1 characters
        if (!result.equals(str.toUpperCase(locale))) {
            System.err.println("Mismatch in uppercase conversion: " + result);
        }
    }
}
