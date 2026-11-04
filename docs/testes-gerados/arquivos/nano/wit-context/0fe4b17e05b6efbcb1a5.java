/* @test    @summary Tests for java.util.Formatter.format(Locale, String, Object...)    @run main FormatterWitupTest */

import java.util.Formatter;
import java.util.Locale;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testFormatWithNullLocale();
        testFormatWithInvalidIndex();
        testFormatWithIndexOutOfBounds();
    }

    private static void testFormatWithNullLocale() {
        Formatter formatter = new Formatter();
        try {
            formatter.format(null, "%s", "test");
        } catch (NullPointerException e) {
            // Expected exception for null locale
        }
    }

    private static void testFormatWithInvalidIndex() {
        Formatter formatter = new Formatter();
        try {
            formatter.format(Locale.US, "%1$*s", "test");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception for invalid index
        }
    }

    private static void testFormatWithIndexOutOfBounds() {
        Formatter formatter = new Formatter();
        try {
            formatter.format(Locale.US, "%2$s", "onlyOneArg");
        } catch (MissingFormatArgumentException e) {
            // Expected exception for missing argument
        }
    }
}
