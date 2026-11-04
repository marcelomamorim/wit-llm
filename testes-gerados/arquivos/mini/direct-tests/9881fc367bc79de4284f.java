/*
 * @test
 * @summary Test java.util.Formatter.toString() method behavior
 * @run main FormatterWitupTest
 */

import java.util.Formatter;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testToStringEmpty();
        testToStringWithContent();
    }

    private static void testToStringEmpty() {
        Formatter formatter = new Formatter();
        String result = formatter.toString();
        if (result == null) {
            throw new AssertionError("toString() returned null on empty Formatter");
        }
        // The default Formatter with no output should return empty string
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string from toString() on new Formatter, got: '" + result + "'");
        }
        formatter.close();
    }

    private static void testToStringWithContent() {
        Formatter formatter = new Formatter();
        formatter.format("Hello %s", "World");
        String result = formatter.toString();
        if (result == null) {
            throw new AssertionError("toString() returned null after formatting");
        }
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World' from toString(), got: '" + result + "'");
        }
        formatter.close();
    }
}
