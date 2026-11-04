/*
 * @test
 * @summary Test String.toLowerCase(Locale) for null locale and normal locale
 * @run main StringWitupTest
 */

import java.util.Locale;

public class StringWitupTest {
    public static void main(String[] args) {
        testToLowerCaseWithLocale();
    }

    private static void testToLowerCaseWithLocale() {
        String s = "ABC";

        // Test with a valid locale: should convert to lower case
        Locale locale = Locale.ENGLISH;
        String lower = s.toLowerCase(locale);
        if (!"abc".equals(lower)) {
            throw new AssertionError("Expected 'abc' but got '" + lower + "'");
        }

        // Test with null locale: according to current code, should throw NullPointerException
        // because locale is passed down and used without null check
        boolean npeThrown = false;
        try {
            s.toLowerCase(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when locale is null");
        }
    }
}
