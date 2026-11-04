/*
 * @test
 * @summary Verifies Formatter.locale() returns the locale given at construction and throws after close
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsConstructorLocale();
        testReturnsSameLocaleOnRepeatedCalls();
        testDistinctLocalesAreReturnedAsIs();
        testLocaleAfterCloseThrows();
    }

    static void testReturnsConstructorLocale() {
        Formatter f = new Formatter(Locale.FRANCE);
        Locale result = f.locale();
        if (!Locale.FRANCE.equals(result)) {
            throw new RuntimeException("Expected FRANCE but got " + result);
        }
    }

    static void testReturnsSameLocaleOnRepeatedCalls() {
        Formatter f = new Formatter(new StringBuilder(), Locale.GERMANY);
        Locale first = f.locale();
        Locale second = f.locale();
        if (!Locale.GERMANY.equals(first) || !first.equals(second)) {
            throw new RuntimeException("Repeated locale() calls differ: " + first + " vs " + second);
        }
    }

    static void testDistinctLocalesAreReturnedAsIs() {
        Formatter fr = new Formatter(new StringBuilder(), Locale.JAPAN);
        Formatter en = new Formatter(new StringBuilder(), Locale.US);
        if (!Locale.JAPAN.equals(fr.locale())) {
            throw new RuntimeException("Expected JAPAN, got " + fr.locale());
        }
        if (!Locale.US.equals(en.locale())) {
            throw new RuntimeException("Expected US, got " + en.locale());
        }
    }

    static void testLocaleAfterCloseThrows() {
        Formatter f = new Formatter(Locale.ITALY);
        f.close();
        boolean thrown = false;
        try {
            f.locale();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("locale() on closed Formatter did not throw IllegalStateException");
        }
    }
}
