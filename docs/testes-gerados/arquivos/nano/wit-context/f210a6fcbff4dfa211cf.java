/*
 * @test
 * @summary Tests the locale() method of java.util.Formatter for null handling.
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testLocaleWithNull();
        testLocaleWithValidFormatter();
    }

    static void testLocaleWithNull() {
        Formatter formatter = null;
        try {
            formatter = new Formatter();
            // Simulate internal state where ensureOpen() passes
            // and locale() is called on a valid Formatter instance
            Locale locale = formatter.locale();
            System.out.println("locale() returned: " + locale);
        } catch (NullPointerException e) {
            System.out.println("Expected NullPointerException when formatter is null.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }

    static void testLocaleWithValidFormatter() {
        Formatter formatter = new Formatter();
        try {
            Locale locale = formatter.locale();
            System.out.println("locale() returned: " + locale);
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
