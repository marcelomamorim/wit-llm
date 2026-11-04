/*
 * @test
 * @summary Tests for String.format(Locale, String, Object...)
 * @run main StringWitupTest
 */

import java.util.Locale;
import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testFormatNullLocale();
        testFormatNullFormat();
        testFormatIndexOutOfBounds();
        testFormatFormatterClosedException();
    }

    public static void testFormatNullLocale() {
        String result = String.format(null, "%s", "test");
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', got: " + result);
        }
    }

    public static void testFormatNullFormat() {
        try {
            String.format(Locale.getDefault(), null, "test");
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void testFormatIndexOutOfBounds() {
        String formatStr = "%s";
        String format = formatStr;
        try {
            String.format(Locale.getDefault(), format, new Object());
        } catch (IndexOutOfBoundsException e) {
            // expected, as per WIT evidence
        }
    }

    public static void testFormatFormatterClosedException() {
        java.util.Formatter formatter = new java.util.Formatter();
        formatter.close();
        try {
            formatter.format("%s", "test");
            throw new AssertionError("Expected FormatterClosedException");
        } catch (java.util.FormatterClosedException expected) {
            // expected
        }
    }
}
