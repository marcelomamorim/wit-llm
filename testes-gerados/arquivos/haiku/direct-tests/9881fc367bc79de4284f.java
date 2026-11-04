/*
 * @test
 * @summary Verifies Formatter.toString() returns the output of the underlying Appendable and fails after close
 * @run main FormatterWitupTest
 */

import java.util.Formatter;

public class FormatterWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testDefaultConstructorEmpty() {
        Formatter f = new Formatter();
        check("".equals(f.toString()), "empty formatter toString should be empty");
    }

    static void testDefaultConstructorAfterFormat() {
        Formatter f = new Formatter();
        f.format("%d", 42);
        check("42".equals(f.toString()), "toString should return formatted output");
    }

    static void testAppendableContentMatches() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format("%s-%s", "a", "b");
        check("a-b".equals(f.toString()), "toString should match appendable content");
        check(sb.toString().equals(f.toString()), "toString should equal StringBuilder content");
    }

    static void testToStringIsRepeatable() {
        Formatter f = new Formatter();
        f.format("x%sy", "z");
        String first = f.toString();
        String second = f.toString();
        check("xzy".equals(first), "first toString incorrect");
        check(first.equals(second), "repeated toString should be stable");
    }

    static void testToStringAfterCloseThrows() {
        Formatter f = new Formatter();
        f.format("abc");
        f.close();
        boolean thrown = false;
        try {
            f.toString();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "toString after close should throw IllegalStateException");
    }

    public static void main(String[] args) {
        testDefaultConstructorEmpty();
        testDefaultConstructorAfterFormat();
        testAppendableContentMatches();
        testToStringIsRepeatable();
        testToStringAfterCloseThrows();
    }
}
