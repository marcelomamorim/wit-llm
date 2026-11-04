/*
 * @test
 * @summary Behavioral checks for Formatter.format(Locale, String, Object...) covering ordinary, explicit and relative indexes, fixed strings, null arguments, missing arguments and return value
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;
import java.util.MissingFormatArgumentException;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testOrdinaryIndexes();
        testExplicitIndex();
        testRelativeIndex();
        testFixedStrings();
        testNullArgsArray();
        testReturnsSameFormatter();
        testMissingOrdinaryArgument();
        testMissingExplicitArgument();
        testRelativeWithoutPrevious();
    }

    private static String fmt(String format, Object... a) {
        StringBuilder sb = new StringBuilder();
        new Formatter(sb).format(Locale.US, format, a);
        return sb.toString();
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }

    private static void testOrdinaryIndexes() {
        check("a b", fmt("%s %s", new Object[]{"a", "b"}));
        check("42", fmt("%d", new Object[]{Integer.valueOf(42)}));
    }

    private static void testExplicitIndex() {
        check("b a", fmt("%2$s %1$s", new Object[]{"a", "b"}));
    }

    private static void testRelativeIndex() {
        check("a a", fmt("%1$s %<s", new Object[]{"a"}));
    }

    private static void testFixedStrings() {
        String nl = System.lineSeparator();
        check("%" + nl, fmt("%%%n", new Object[0]));
        check("x%y", fmt("x%%y", new Object[0]));
    }

    private static void testNullArgsArray() {
        check("null", fmt("%s", (Object[]) null));
        check("lit", fmt("lit", (Object[]) null));
    }

    private static void testReturnsSameFormatter() {
        Formatter f = new Formatter(new StringBuilder());
        Formatter r = f.format(Locale.US, "%s", new Object[]{"z"});
        if (r != f) {
            throw new RuntimeException("format must return this");
        }
    }

    private static void expectMissing(String format, Object[] a) {
        try {
            new Formatter(new StringBuilder()).format(Locale.US, format, a);
        } catch (MissingFormatArgumentException e) {
            return;
        }
        throw new RuntimeException("MissingFormatArgumentException expected for " + format);
    }

    private static void testMissingOrdinaryArgument() {
        expectMissing("%s %s", new Object[]{"only"});
    }

    private static void testMissingExplicitArgument() {
        expectMissing("%3$s", new Object[]{"a", "b"});
    }

    private static void testRelativeWithoutPrevious() {
        expectMissing("%<s", new Object[]{"a"});
    }
}
