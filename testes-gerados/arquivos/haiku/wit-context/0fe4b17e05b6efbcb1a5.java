/*
 * @test
 * @summary Witup generated tests for Formatter.format(Locale, String, Object...)
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

public class FormatterWitupTest {

    public static void main(String[] args) throws Exception {
        testClosedFormatterThrows();
        testBasicFormatting();
        testMissingArgumentThrows();
        testNullArgumentArray();
        testExplicitIndex();
        testFixedStrings();
        testUnknownConversionThrows();
        testReturnsThis();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testClosedFormatterThrows() {
        Formatter f = new Formatter(new StringBuilder());
        f.close();
        try {
            f.format(Locale.US, "%s", "a");
            throw new RuntimeException("FormatterClosedException not thrown");
        } catch (FormatterClosedException expected) {
            // expected
        }
    }

    static void testBasicFormatting() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format(Locale.US, "%d-%s", 42, "x");
        check("42-x".equals(sb.toString()), "unexpected output: " + sb);
    }

    static void testMissingArgumentThrows() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        try {
            f.format(Locale.US, "%s %s", "a");
            throw new RuntimeException("MissingFormatArgumentException not thrown");
        } catch (MissingFormatArgumentException expected) {
            // expected
        }
    }

    static void testNullArgumentArray() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format(Locale.US, "%s", (Object[]) null);
        check("null".equals(sb.toString()), "unexpected output: " + sb);
    }

    static void testExplicitIndex() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format(Locale.US, "%2$s %1$s", "a", "b");
        check("b a".equals(sb.toString()), "unexpected output: " + sb);
    }

    static void testFixedStrings() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format(Locale.US, "100%%");
        check("100%".equals(sb.toString()), "unexpected output: " + sb);
    }

    static void testUnknownConversionThrows() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        try {
            f.format(Locale.US, "%q");
            throw new RuntimeException("UnknownFormatConversionException not thrown");
        } catch (UnknownFormatConversionException expected) {
            // expected
        }
    }

    static void testReturnsThis() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        Formatter r = f.format(Locale.US, "%s", "a");
        check(r == f, "format must return this");
    }
}
