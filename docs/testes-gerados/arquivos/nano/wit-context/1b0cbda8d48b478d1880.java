/* @test    @summary Test for DecimalStyle.of(Locale) method, including null input handling    @run main DecimalStyleWitupTest */

import java.time.format.DecimalStyle;
import java.util.Locale;

public class DecimalStyleWitupTest {

    public static void main(String[] args) {
        testOfWithValidLocale();
        testOfWithNullLocale();
    }

    static void testOfWithValidLocale() {
        Locale locale = Locale.US;
        DecimalStyle result = DecimalStyle.of(locale);
        if (result == null) {
            throw new AssertionError("Expected non-null DecimalStyle for locale: " + locale);
        }
        // Additional observable checks can be added if needed
    }

    static void testOfWithNullLocale() {
        try {
            DecimalStyle result = DecimalStyle.of(null);
            throw new AssertionError("Expected NullPointerException when passing null locale");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
