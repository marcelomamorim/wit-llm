/*
 * @test
 * @summary Deterministic tests for java.util.Formatter.format(String, Object...)
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;
import java.util.FormatterClosedException;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testFormatBasic();
        testFormatClosedFormatter();
        testFormatNullFormatString();
    }

    private static void testFormatBasic() {
        Formatter f = new Formatter();
        String result = f.format("Hello %s", "World").toString();
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World' but got: " + result);
        }
        f.close();
    }

    private static void testFormatClosedFormatter() {
        Formatter f = new Formatter();
        f.close();
        try {
            f.format("test");
            throw new AssertionError("Expected FormatterClosedException not thrown");
        } catch (FormatterClosedException expected) {
            // Expected exception
        }
    }

    private static void testFormatNullFormatString() {
        Formatter f = new Formatter();
        try {
            f.format(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } finally {
            f.close();
        }
    }
}
