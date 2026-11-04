/* @test    @summary Test String.toUpperCase(Locale) method for different locales    @run main StringWitupTest */

import java.lang.String;
import java.util.Locale;

public class StringWitupTest {

    public static void main(String[] args) {
        testToUpperCaseDefaultLocale();
        testToUpperCaseSpecificLocale();
    }

    private static void testToUpperCaseDefaultLocale() {
        String original = "abc";
        String result = original.toUpperCase(Locale.getDefault());
        String expected = "ABC";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected '" + expected + "', but got '" + result + "'");
        }
    }

    private static void testToUpperCaseSpecificLocale() {
        String original = "i";
        String resultTR = original.toUpperCase(new Locale("tr", "TR"));
        String expectedTR = "İ"; // Turkish dotted capital I
        if (!resultTR.equals(expectedTR)) {
            throw new AssertionError("Expected '" + expectedTR + "', but got '" + resultTR + "'");
        }
        String resultEN = original.toUpperCase(Locale.ENGLISH);
        String expectedEN = "I";
        if (!resultEN.equals(expectedEN)) {
            throw new AssertionError("Expected '" + expectedEN + "', but got '" + resultEN + "'");
        }
    }
}
