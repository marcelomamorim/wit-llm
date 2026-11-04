/*
 * @test
 * @summary Behavioral checks for String.format(Locale, String, Object...) including null locale, null format and malformed format specifiers
 * @run main StringWitupTest
 */

import java.util.IllegalFormatConversionException;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

public class StringWitupTest {

    public static void main(String[] args) {
        check("42-x", String.format(Locale.US, "%d-%s", 42, "x"));
        check("1,234,567", String.format(Locale.US, "%,d", 1234567));
        check("100%", String.format(Locale.US, "100%%"));
        check("null", String.format(Locale.US, "%s", (Object) null));
        check("a  |", String.format(Locale.US, "%-3s|", "a"));
        check("5", String.format((Locale) null, "%d", 5));
        check("", String.format(Locale.US, ""));

        expectThrows(NullPointerException.class,
                () -> String.format(Locale.US, (String) null));
        expectThrows(UnknownFormatConversionException.class,
                () -> String.format(Locale.US, "%q", "x"));
        expectThrows(MissingFormatArgumentException.class,
                () -> String.format(Locale.US, "%s"));
        expectThrows(IllegalFormatConversionException.class,
                () -> String.format(Locale.US, "%d", "x"));
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }

    private static void expectThrows(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("expected " + type.getName() + " but got " + t, t);
        }
        throw new RuntimeException("expected " + type.getName() + " but nothing was thrown");
    }
}
