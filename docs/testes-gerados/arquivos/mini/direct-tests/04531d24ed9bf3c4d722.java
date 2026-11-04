/*
 * @test
 * @summary Test java.lang.String.format(String, Object...) basic functionality and edge cases
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testSimpleFormat();
        testFormatWithMultipleArgs();
        testFormatWithNullFormat();
        testFormatWithNullArgs();
        testFormatWithEmptyFormat();
    }

    private static void testSimpleFormat() {
        String result = String.format("Hello %s", "World");
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World' but got: " + result);
        }
    }

    private static void testFormatWithMultipleArgs() {
        String result = String.format("Int: %d, Float: %.1f, String: %s", 42, 3.14, "test");
        if (!"Int: 42, Float: 3.1, String: test".equals(result)) {
            throw new AssertionError("Unexpected format result: " + result);
        }
    }

    private static void testFormatWithNullFormat() {
        try {
            String.format(null, "arg");
            throw new AssertionError("Expected NullPointerException for null format string");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testFormatWithNullArgs() {
        // Null args array is allowed and treated as empty
        String result = String.format("Hello World", (Object[]) null);
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World' but got: " + result);
        }
    }

    private static void testFormatWithEmptyFormat() {
        String result = String.format("");
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string but got: " + result);
        }
    }
}
