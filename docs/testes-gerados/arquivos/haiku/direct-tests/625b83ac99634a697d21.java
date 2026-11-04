/*
 * @test
 * @summary String.format(Locale, String, Object...) delegates to Formatter using the given locale
 * @run main StringWitupTest
 */

import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

public class StringWitupTest {

    private interface Action {
        void run() throws Exception;
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but was [" + actual + "]");
        }
    }

    private static void expectThrows(Class<? extends Throwable> type, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected " + type.getName());
    }

    public static void main(String[] args) {
        check("42", String.format(Locale.US, "%d", 42));
        check("a-b", String.format(Locale.US, "%s-%s", "a", "b"));
        check("plain", String.format(Locale.US, "plain"));
        check("1,234,567", String.format(Locale.US, "%,d", 1234567));
        check("3.14", String.format(Locale.US, "%.2f", 3.14159));
        check("7", String.format((Locale) null, "%d", 7));
        expectThrows(UnknownFormatConversionException.class,
                () -> String.format(Locale.US, "%q", 1));
        expectThrows(MissingFormatArgumentException.class,
                () -> String.format(Locale.US, "%s"));
    }
}
