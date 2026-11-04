/*
 * @test
 * @summary Verifies Formatter.toString() returns the underlying Appendable output and throws when the Formatter is closed
 * @run main FormatterWitupTest
 */

import java.util.Formatter;

public class FormatterWitupTest {
    public static void main(String[] args) {
        testEmptyFormatterReturnsEmptyString();
        testToStringReflectsFormattedOutput();
        testToStringWithAppendableReturnsAppendableContent();
        testToStringIsRepeatable();
        testToStringAfterCloseThrowsIllegalStateException();
    }

    static void testEmptyFormatterReturnsEmptyString() {
        Formatter f = new Formatter();
        check("".equals(f.toString()), "empty formatter toString must be empty");
    }

    static void testToStringReflectsFormattedOutput() {
        Formatter f = new Formatter();
        f.format("x%d", 5);
        check("x5".equals(f.toString()), "toString must return formatted output");
    }

    static void testToStringWithAppendableReturnsAppendableContent() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format("%s", "z");
        String s = f.toString();
        check("z".equals(s), "toString must return appendable content");
        check(s.equals(sb.toString()), "toString must match the appendable");
    }

    static void testToStringIsRepeatable() {
        Formatter f = new Formatter();
        f.format("%s-%s", "a", "b");
        String first = f.toString();
        String second = f.toString();
        check("a-b".equals(first), "first toString call incorrect");
        check(first.equals(second), "repeated toString calls must be equal");
    }

    static void testToStringAfterCloseThrowsIllegalStateException() {
        Formatter f = new Formatter();
        f.format("abc");
        f.close();
        boolean thrown = false;
        try {
            f.toString();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "toString after close must throw IllegalStateException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
