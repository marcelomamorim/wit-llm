/*
 * @test
 * @summary Basic behavior of String.format delegating to Formatter
 * @run main StringWitupTest
 */

import java.util.IllegalFormatException;

public class StringWitupTest {

    public static void main(String[] args) {
        check("abc", String.format("abc"));
        check("x=42", String.format("x=%d", 42));
        check("hello world", String.format("%s %s", "hello", "world"));
        check("100%", String.format("100%%"));
        check("[null]", String.format("[%s]", (Object) null));
        check("7-true", String.format("%d-%b", 7, Boolean.TRUE));
        check("ff", String.format("%x", 255));
        check("a|b", String.format("%s|%s", "a", "b"));
        check("", String.format(""));

        expectNullPointerException();
        expectIllegalFormatException();
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but got [" + actual + "]");
        }
    }

    private static void expectNullPointerException() {
        try {
            String.format(null, 1);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null format");
    }

    private static void expectIllegalFormatException() {
        try {
            String.format("%q", "x");
        } catch (IllegalFormatException e) {
            return;
        }
        throw new RuntimeException("IllegalFormatException expected for unknown conversion");
    }
}
