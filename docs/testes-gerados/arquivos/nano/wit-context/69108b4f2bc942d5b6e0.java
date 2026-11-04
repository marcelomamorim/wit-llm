/*
 * @test
 * @summary Tests for String.toUpperCase(Locale)
 * @run main StringWitupTest
 */

import java.lang.String;
import java.util.Locale;

public class StringWitupTest {

    public static void main(String[] args) {
        testToUpperCaseWithNullLocale();
        testToUpperCaseWithLatin1AndNullLocale();
    }

    private static void testToUpperCaseWithNullLocale() {
        String s = "test";
        try {
            String result = s.toUpperCase(null);
            // Expecting NullPointerException
            System.err.println("Expected NullPointerException for null locale, but got: " + result);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null locale.");
        }
    }

    private static void testToUpperCaseWithLatin1AndNullLocale() {
        String s = "test";
        try {
            String result = s.toUpperCase(null);
            // Expecting NullPointerException
            System.err.println("Expected NullPointerException for null locale, but got: " + result);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null locale.");
        }
    }
}
