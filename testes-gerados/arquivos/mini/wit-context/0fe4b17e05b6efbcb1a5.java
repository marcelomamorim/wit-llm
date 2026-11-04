/*
 * @test
 * @summary Deterministic tests for java.util.Formatter.format(Locale, String, Object...)
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.FormatterClosedException;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testFormatOnClosedFormatter();
        testFormatWithNullFormatString();
        testFormatWithMissingArguments();
        testFormatWithValidInputs();
    }

    private static void testFormatOnClosedFormatter() {
        Formatter f = new Formatter();
        f.close();
        try {
            f.format(Locale.US, "test %s", "arg");
            throw new RuntimeException("Expected FormatterClosedException not thrown");
        } catch (FormatterClosedException expected) {
            // expected
        }
    }

    private static void testFormatWithNullFormatString() {
        Formatter f = new Formatter();
        try {
            f.format(Locale.US, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            f.close();
        }
    }

    private static void testFormatWithMissingArguments() {
        Formatter f = new Formatter();
        try {
            // Format string expects one argument but none provided
            f.format(Locale.US, "%s");
            throw new RuntimeException("Expected MissingFormatArgumentException not thrown");
        } catch (MissingFormatArgumentException expected) {
            // expected
        } finally {
            f.close();
        }
    }

    private static void testFormatWithValidInputs() {
        Formatter f = new Formatter();
        try {
            // Simple valid formatting
            f.format(Locale.US, "Hello %s %d", "World", 2021);
            String result = f.toString();
            if (!result.equals("Hello World 2021")) {
                throw new RuntimeException("Unexpected format output: " + result);
            }
        } finally {
            f.close();
        }
    }
}
