/*
 * @test
 * @summary Tests the format method of java.util.Formatter for various inputs.
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;
import java.io.IOException;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testFormatWithNullLocale();
        testFormatWithEmptyFormat();
        testFormatWithSimpleString();
        testFormatWithInteger();
        testFormatWithMultipleArgs();
        testFormatWithInvalidIndex();
        testFormatWithNullArgs();
        System.out.println("All tests passed.");
    }

    static void testFormatWithNullLocale() {
        Formatter formatter = new Formatter();
        formatter.format(null, "Hello %s", "World");
        String result = formatter.toString();
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World', got: " + result);
        }
    }

    static void testFormatWithEmptyFormat() {
        Formatter formatter = new Formatter();
        formatter.format(Locale.US, "");
        String result = formatter.toString();
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, got: " + result);
        }
    }

    static void testFormatWithSimpleString() {
        Formatter formatter = new Formatter();
        formatter.format(Locale.US, "Simple string");
        String result = formatter.toString();
        if (!"Simple string".equals(result)) {
            throw new AssertionError("Expected 'Simple string', got: " + result);
        }
    }

    static void testFormatWithInteger() {
        Formatter formatter = new Formatter();
        formatter.format(Locale.US, "Number: %d", 42);
        String result = formatter.toString();
        if (!"Number: 42".equals(result)) {
            throw new AssertionError("Expected 'Number: 42', got: " + result);
        }
    }

    static void testFormatWithMultipleArgs() {
        Formatter formatter = new Formatter();
        formatter.format(Locale.US, "%s %d %f", "Test", 10, 3.14);
        String result = formatter.toString();
        if (!"Test 10 3.14".equals(result)) {
            throw new AssertionError("Expected 'Test 10 3.14', got: " + result);
        }
    }

    static void testFormatWithInvalidIndex() {
        Formatter formatter = new Formatter();
        boolean exceptionThrown = false;
        try {
            formatter.format(Locale.US, "%3$s", "a", "b");
        } catch (java.util.MissingFormatArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected MissingFormatArgumentException for invalid index");
        }
    }

    static void testFormatWithNullArgs() {
        Formatter formatter = new Formatter();
        formatter.format(Locale.US, "Hello %s", (Object) null);
        String result = formatter.toString();
        if (!"Hello null".equals(result)) {
            throw new AssertionError("Expected 'Hello null', got: " + result);
        }
    }
}
