/*
 * @test
 * @summary Test java.util.Formatter.locale() method
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;
import java.io.IOException;

public class FormatterWitupTest {
    public static void main(String[] args) throws IOException {
        // Test default constructor locale
        Formatter f1 = new Formatter();
        Locale loc1 = f1.locale();
        if (loc1 == null) {
            throw new AssertionError("Formatter.locale() returned null for default constructor");
        }

        // Test constructor with Locale
        Locale testLocale = Locale.FRANCE;
        Formatter f2 = new Formatter(testLocale);
        Locale loc2 = f2.locale();
        if (!testLocale.equals(loc2)) {
            throw new AssertionError("Formatter.locale() did not return the locale passed to constructor");
        }

        // Test constructor with Appendable and Locale
        StringBuilder sb = new StringBuilder();
        Formatter f3 = new Formatter(sb, testLocale);
        Locale loc3 = f3.locale();
        if (!testLocale.equals(loc3)) {
            throw new AssertionError("Formatter.locale() did not return the locale passed to constructor with Appendable");
        }

        // Test that calling locale() on closed Formatter throws IllegalStateException
        Formatter f4 = new Formatter();
        f4.close();
        boolean threw = false;
        try {
            f4.locale();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Formatter.locale() did not throw IllegalStateException after close");
        }
    }
}
