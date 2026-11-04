/*
 * @test
 * @summary Tests the format method of java.util.Formatter for various inputs.
 * @run main FormatterWitupTest
 */

import java.util.Formatter;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testSimpleFormatting();
        testNullArguments();
        testEmptyFormatString();
        testMultipleArguments();
        testFormatWithSpecialCharacters();
    }

    static void testSimpleFormatting() {
        Formatter formatter = new Formatter();
        formatter.format("Hello, %s!", "World");
        String result = formatter.out().toString();
        if (!result.contains("Hello, World!")) {
            throw new AssertionError("Simple formatting failed: " + result);
        }
        formatter.close();
    }

    static void testNullArguments() {
        Formatter formatter = new Formatter();
        formatter.format("Null object: %s", (Object) null);
        String result = formatter.out().toString();
        if (!result.contains("Null object: null")) {
            throw new AssertionError("Null argument formatting failed: " + result);
        }
        formatter.close();
    }

    static void testEmptyFormatString() {
        Formatter formatter = new Formatter();
        formatter.format("");
        String result = formatter.out().toString();
        if (!result.isEmpty()) {
            throw new AssertionError("Empty format string should produce empty output: " + result);
        }
        formatter.close();
    }

    static void testMultipleArguments() {
        Formatter formatter = new Formatter();
        formatter.format("Multiple: %d, %f, %s", 42, 3.14, "test");
        String result = formatter.out().toString();
        if (!result.contains("42") || !result.contains("3.14") || !result.contains("test")) {
            throw new AssertionError("Multiple arguments formatting failed: " + result);
        }
        formatter.close();
    }

    static void testFormatWithSpecialCharacters() {
        Formatter formatter = new Formatter();
        formatter.format("Special chars: %% %n %t", 1);
        String result = formatter.out().toString();
        if (!result.contains("%%") || !result.contains("\n") || !result.contains("1")) {
            throw new AssertionError("Special characters formatting failed: " + result);
        }
        formatter.close();
    }
}
