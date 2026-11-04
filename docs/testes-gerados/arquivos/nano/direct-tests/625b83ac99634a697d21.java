/*
 * @test
 * @summary Tests String.format(Locale, String, Object...) for correctness and regressions
 * @run main StringWitupTest
 */

import java.util.Locale;

public class StringWitupTest {

    public static void main(String[] args) {
        testFormatWithLocaleAndArguments();
        testFormatWithNullLocale();
        testFormatWithEmptyFormat();
        testFormatWithNullArguments();
    }

    private static void testFormatWithLocaleAndArguments() {
        String result = String.format(Locale.US, "Number: %d, String: %s", 42, "hello");
        if (!"Number: 42, String: hello".equals(result)) {
            throw new AssertionError("Failed: testFormatWithLocaleAndArguments, got: " + result);
        }
    }

    private static void testFormatWithNullLocale() {
        String result = String.format(null, "Value: %f", 3.14);
        if (!"Value: 3.140000".equals(result)) {
            throw new AssertionError("Failed: testFormatWithNullLocale, got: " + result);
        }
    }

    private static void testFormatWithEmptyFormat() {
        String result = String.format(Locale.ENGLISH, "", 1, 2, 3);
        if (!"".equals(result)) {
            throw new AssertionError("Failed: testFormatWithEmptyFormat, got: " + result);
        }
    }

    private static void testFormatWithNullArguments() {
        String result = String.format(Locale.CANADA, "Test: %s", (Object) null);
        if (!"Test: null".equals(result)) {
            throw new AssertionError("Failed: testFormatWithNullArguments, got: " + result);
        }
    }
}
